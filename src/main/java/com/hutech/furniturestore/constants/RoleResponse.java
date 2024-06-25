package com.hutech.furniturestore.constants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    @Schema(description = "The unique identifier of the role", example = "1")
    private Long id;
    @Schema(description = "The name of the role", example = "Admin")
    private String name;
    @Schema(description = "The description of the role", example = "Administrator role")
    private String description;
}
