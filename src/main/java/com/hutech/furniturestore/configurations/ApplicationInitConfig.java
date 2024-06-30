package com.hutech.furniturestore.configurations;

import com.hutech.furniturestore.constants.PredefinedRole;
import com.hutech.furniturestore.models.Role;
import com.hutech.furniturestore.models.User;
import com.hutech.furniturestore.repositories.RoleRepository;
import com.hutech.furniturestore.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_EMAIL = "admin@gmail.com";
    @NonFinal
    static final String ADMIN_USER_NAME = "admin";
    @NonFinal
    static final String ADMIN_PASSWORD = "Admin@123";
    @NonFinal
    static final String ADMIN_FIRST_NAME = "Ngoc Uyen";
    @NonFinal
    static final String ADMIN_LAST_NAME = "Le";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .email(ADMIN_EMAIL)
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .firstName(ADMIN_FIRST_NAME)
                        .lastName(ADMIN_LAST_NAME)
                        .roles(roles)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
