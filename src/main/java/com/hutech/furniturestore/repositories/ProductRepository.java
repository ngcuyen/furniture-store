package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
