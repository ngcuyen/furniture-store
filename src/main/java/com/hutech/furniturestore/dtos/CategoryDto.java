package com.hutech.furniturestore.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String name;
    private String description;
    private Boolean isRemoved = false;
}
