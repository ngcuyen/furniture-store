package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
