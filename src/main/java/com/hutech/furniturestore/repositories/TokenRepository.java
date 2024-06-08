package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
