package com.hutech.furniturestore.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

        private String name;
        private String imageUrl;
        private String description;
        private String stockQuantity;
        private String thumbnail;
        private Long categoryId;
        private Integer quantity;
        private Integer sold;
        private Boolean isRemoved = false;
        private Boolean isBestSeller = false;
        private Double price;
        private Boolean isAvailable = true;


}
