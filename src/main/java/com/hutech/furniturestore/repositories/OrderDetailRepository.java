package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
