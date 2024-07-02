package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.constants.OrderResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.request.OrderDto;
import com.hutech.furniturestore.sevices.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderResponse orderResponse = orderService.createOrder(orderDto);
        ApiResponse<OrderResponse> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Order created successfully",
                orderResponse,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
}
