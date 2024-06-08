package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
