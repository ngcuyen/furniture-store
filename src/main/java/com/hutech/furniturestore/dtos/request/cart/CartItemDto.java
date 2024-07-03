package com.hutech.furniturestore.dtos.request.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDto {
    private Long productId;
    private Integer quantity;
    private Double price;
}
