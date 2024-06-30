package com.hutech.furniturestore.models;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, unique = true)
    String email;
    String username;
    @Column(nullable = false)
    String password;
    String firstName;
    String lastName;
    String thumbnail;
    String avatar;
    String phone;
    String address;
    boolean active = false;
    boolean blocked = false;
    Date blockedAt;
    int facebookAccountId;
    int googleAccountId;

    @ManyToMany
    Set<Role> roles;
}
