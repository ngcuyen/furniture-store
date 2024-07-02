package com.hutech.furniturestore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "number_of_products", nullable = false)
    private Integer numberOfProducts;

    @Column(name = "price", nullable = false)
    private Double price;
}
