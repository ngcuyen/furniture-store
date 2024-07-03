package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Page<Cart> findByUserId(String userId, Pageable pageable);
    Optional<Cart> findByUserId(String userId);
}
