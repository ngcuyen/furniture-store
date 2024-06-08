package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
