package com.hutech.furniturestore.constants;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Details about the category")
public class CategoryResponse {
    @Schema(description = "The unique identifier of the category", example = "1")
    private Long id;

    @Schema(description = "The name of the category", example = "bedroom")
    private String name;

    @Schema(description = "The description of the category", example = "one of a kind")
    private String description;

    @Schema(description = "The removed status of the category", example = "false")
    private Boolean isRemoved = false;
}
