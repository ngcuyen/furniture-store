package com.hutech.furniturestore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "coupons")
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "discount_percentage", nullable = false)
    private double discountPercentage;

    @Column(name = "valid_form")
    private Date validFrom;

    @Column(name = "valid_until")
    private Date validUntil;

    @Column(name = "minimum_amount", nullable = false)
    private int minimumAmount;

    @Column(name = "is_removed")
    private Boolean isRemoved = false;

    @Column(name = "is_active")
    private Boolean isActive = false;
}
