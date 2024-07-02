package com.hutech.furniturestore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 10)
    private String phone;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "note", length = 300)
    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_date")
    private Date shippingDate;

    @Column(name = "payment_method", length = 100)
    private String paymentMethod;

    // Owned by admin
    @Column(name = "active")
    private Boolean active = false;

    @Column(name = "is_removed")
    private boolean isRemoved = false;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
