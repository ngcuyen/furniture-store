package com.hutech.furniturestore.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateProductDto {
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateProductDto {
        private String product_id;
        private String name;
        private String imageUrl;
        private String description;
        private String stockQuantity;
        private String categoryId;
        private boolean isDelete;
        private boolean isAvailable;
        private boolean bestSeller;
    }
}
