package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.OrderResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.sevices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<PaginationResponse<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        PaginationResponse<OrderResponse> response = orderService.getAllOrdersPaginationAdmin(page, size, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
