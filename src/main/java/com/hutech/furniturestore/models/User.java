package com.hutech.furniturestore.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @NotBlank(message = "Id is required")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NotBlank(message = "Email is required")
    private String email;
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String phone;
    private String address;
    private Boolean isDelete;
    private String createdAt;
    private String updatedAt;
    private String role;
}
