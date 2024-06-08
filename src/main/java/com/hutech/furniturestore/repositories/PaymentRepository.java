package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
