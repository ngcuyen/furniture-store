package com.hutech.furniturestore.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @NotEmpty(message = "Role is required")
    @Size(min = 3, max = 20, message = "Role must be between 3 and 20 characters")
    @NotNull
    private String name;

    @Size(max = 20, message = "Description must not exceed 200 characters")
    private String description;
}
