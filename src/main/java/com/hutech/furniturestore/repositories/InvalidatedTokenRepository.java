package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<Token, String> {
}
