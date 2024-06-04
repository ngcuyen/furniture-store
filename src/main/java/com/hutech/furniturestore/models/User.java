package com.hutech.furniturestore.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "username", length = 50)
    private String userName;

    @Column(name = "uid", length = 100)
    private String uid;

    @Column(name = "first_name", length = 60, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "cover_url", length = 300)
    private String coverUrl;

    @Column(name = "avatar_url", length = 300)
    private String avatarUrl;

    @Column(name = "phone_number", length = 10)
    private String phone;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "is_removed")
    private Boolean isRemoved = false;

    @Column(name = "is_blocked")
    private Boolean isBlocked = false;

    @Column(name = "blocked_at")
    private Date blockedAt;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @OneToMany(mappedBy = "user")
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<SocialAccount> socialAccounts;
}
