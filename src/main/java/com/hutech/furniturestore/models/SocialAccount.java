package com.hutech.furniturestore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "social_accounts")
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccount extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider", length = 50, nullable = false)
    private String provider;

    @Column(name = "provider_id", length = 50, nullable = false)
    private String provider_id;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "email", length = 150)
    private String email;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_removed")
    private Boolean isRemoved = false;
}
