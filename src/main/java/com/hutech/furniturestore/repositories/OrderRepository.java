package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
