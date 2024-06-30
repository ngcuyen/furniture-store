package com.hutech.furniturestore.dtos.response.user;

import com.hutech.furniturestore.models.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String email;
    String username;
    String firstName;
    String lastName;
    String thumbnail;
    String avatar;
    String phone;
    String address;
    boolean active;
    boolean blocked;
    Set<Role> roles;
}

