package com.hutech.furniturestore.constants;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationResponse<T> {
    private List<T> items;
    private int page;
    private int perPage;
    private int totalPages;
    private long totalItems;
}