package com.hutech.furniturestore.constants;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Details about the product")
public class ProductResponse {
    @Schema(description = "The unique identifier of the product", example = "1")
    private Long id;

    @Schema(description = "The name of the product", example = "wardrobe")
    private String name;

    @Schema(description = "The description of the product", example = "one of a kind")
    private String description;

    @Schema(description = "The price of the product")
    private Double price;

    @Schema(description = "The thumbnail of the product")
    private String thumbnail;

    @Schema(description = "The quantity of the product")
    private Integer quantity;

    @Schema(description = "The sold of the product")
    private Integer sold;

    @Schema(description = "The price of the product")
    private Boolean isAvailable;

    @Schema(description = "The price of the product")
    private Boolean isBestSeller = false;

    @Schema(description = "The price of the product")
    private Boolean isRemoved = false;
}
