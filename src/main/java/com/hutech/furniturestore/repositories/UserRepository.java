package com.hutech.furniturestore.repositories;

import com.hutech.furniturestore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>  {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<User> findByActiveFalse();

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
