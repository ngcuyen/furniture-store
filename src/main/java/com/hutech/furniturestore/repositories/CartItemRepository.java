package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
