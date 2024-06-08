package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
