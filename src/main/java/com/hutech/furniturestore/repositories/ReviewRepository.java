package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
