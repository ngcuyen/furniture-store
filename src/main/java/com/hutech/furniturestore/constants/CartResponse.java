package com.hutech.furniturestore.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long productId;
    private String thumbnail;
    private String productName;
    private double productPrice;
    private int quantity;
    private Double totalPrice;
}
