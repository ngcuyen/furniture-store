package com.hutech.furniturestore.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer numberOfProducts;
    private Double totalPrice;
}
