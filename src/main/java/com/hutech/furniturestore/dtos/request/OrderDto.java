package com.hutech.furniturestore.dtos.request;

import com.hutech.furniturestore.dtos.request.cart.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String customerName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private String paymentMethod;
    private List<CartItemDto> cartItems;
}
