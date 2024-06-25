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
        private String categoryId;
        private boolean isDelete = false;
        private boolean isAvailable = true;
        private boolean bestSeller = false;
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
