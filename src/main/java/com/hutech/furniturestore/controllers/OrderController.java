package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.constants.OrderResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.request.OrderDto;
import com.hutech.furniturestore.sevices.OrderService;
import com.hutech.furniturestore.sevices.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");

    @Autowired
    private OrderService orderService;

    @Autowired
    private VNPayService vnPayService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderResponse orderResponse = orderService.createOrder(orderDto);

        // Tính toán tổng số tiền từ OrderDto
        double totalAmount = orderResponse.getTotalPrice();

        // Thông tin đơn hàng
        String orderInfo = "Thông tin đơn hàng";
        String urlReturn = "http://localhost:8080/";

        // Tạo URL thanh toán VNPay
        String paymentUrl = vnPayService.createOrderVNPay((int) (totalAmount*100), orderInfo, urlReturn);

        // Lưu URL thanh toán vào đơn hàng
        orderService.updateOrderPaymentUrl(orderResponse.getId(), paymentUrl);

        // Thêm URL thanh toán vào phản hồi
        orderResponse.setPaymentUrl(paymentUrl);

        ApiResponse<OrderResponse> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Order created successfully",
                orderResponse,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private int calculateTotalAmount(OrderDto orderDto) {
        return orderDto.getCartItems().stream()
                .mapToInt(cartItem -> cartItem.getQuantity() * getProductPrice(cartItem.getProductId()))
                .sum();
    }

    private int getProductPrice(Long productId) {
        // Lấy giá sản phẩm từ productId, đây chỉ là ví dụ
        return 100000;
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortOrder) {
        PaginationResponse<OrderResponse> response = orderService.getAllOrdersPagination(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(response);
    }



    // Endpoint để nhận kết quả từ VNPay sau thanh toán
    @GetMapping("/payment/result")
    public ResponseEntity<Map<String, String>> processVNPayCallback(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        Map<String, String> responseData = new HashMap<>();
        responseData.put("orderId", orderInfo);
        responseData.put("totalPrice", totalPrice);
        responseData.put("paymentTime", paymentTime);
        responseData.put("transactionId", transactionId);

        // Kiểm tra và trả về mã HTTP phù hợp dựa trên trạng thái thanh toán
        HttpStatus status = (paymentStatus == 1) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(responseData, status);
    }
}
