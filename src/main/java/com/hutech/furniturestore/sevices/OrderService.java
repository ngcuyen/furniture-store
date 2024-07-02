package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.component.SecurityUtil;
import com.hutech.furniturestore.constants.OrderResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.request.OrderDetailDto;
import com.hutech.furniturestore.dtos.request.OrderDto;
import com.hutech.furniturestore.dtos.request.cart.CartItemDto;
import com.hutech.furniturestore.models.Order;
import com.hutech.furniturestore.models.OrderDetail;
import com.hutech.furniturestore.models.Product;
import com.hutech.furniturestore.models.User;
import com.hutech.furniturestore.repositories.OrderDetailRepository;
import com.hutech.furniturestore.repositories.OrderRepository;
import com.hutech.furniturestore.repositories.ProductRepository;
import com.hutech.furniturestore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Transactional
    public OrderResponse createOrder(OrderDto orderDto) {
        String currentUserId = securityUtil.getCurrentUserId();
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + currentUserId));


        Order order = new Order();
        order.setFullName(orderDto.getCustomerName());
        order.setPhone(orderDto.getPhone());
        order.setAddress(orderDto.getAddress());
        order.setNote(orderDto.getNote());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending");
        order.setUser(currentUser);

        double totalOrderPrice = 0.0;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (CartItemDto itemDto : orderDto.getCartItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemDto.getProductId()));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setNumberOfProducts(itemDto.getQuantity());
            detail.setPrice(itemDto.getQuantity() * product.getPrice());  // Tính toán giá trị của total_price
            orderDetails.add(detail);
            totalOrderPrice += detail.getPrice();
        }

        order.setTotalPrice(totalOrderPrice);
        order.setOrderDetails(orderDetails);

        order = orderRepository.save(order);

        for (OrderDetail detail : orderDetails) {
            orderDetailRepository.save(detail);
        }

        cartService.clearCart(); // Clear the cart after order placement

        return convertToOrderResponse(order);
    }

    private OrderResponse convertToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        response.setFullName(order.getFullName());
        response.setPhone(order.getPhone());
        response.setAddress(order.getAddress());
        response.setNote(order.getNote());
        response.setOrderDate(order.getOrderDate());
        response.setShippingDate(order.getShippingDate());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setRemoved(order.isRemoved());

        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        for (OrderDetail detail : order.getOrderDetails()) {
            OrderDetailDto detailDto = new OrderDetailDto();
            detailDto.setId(detail.getId());
            detailDto.setProductId(detail.getProduct().getId());
            detailDto.setProductName(detail.getProduct().getName());
            detailDto.setNumberOfProducts(detail.getNumberOfProducts());
            detailDto.setTotalPrice(detail.getPrice());
            orderDetailDtos.add(detailDto);
        }
        response.setOrderDetails(orderDetailDtos);

        return response;
    }

    public PaginationResponse<OrderResponse> getAllOrdersPagination(int page, int size, String sortBy, String sortOrder) {
        String currentUserId = securityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<Order> orderPage = orderRepository.findByUserId(currentUserId, pageable);
        List<OrderResponse> orderResponses = orderPage.getContent().stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());

        PaginationResponse<OrderResponse> paginatedResponse = new PaginationResponse<>();
        paginatedResponse.setItems(orderResponses);
        paginatedResponse.setPage(orderPage.getNumber() + 1);
        paginatedResponse.setPerPage(orderPage.getSize());
        paginatedResponse.setTotalPages(orderPage.getTotalPages());
        paginatedResponse.setTotalItems(orderPage.getTotalElements());

        return paginatedResponse;
    }
}
