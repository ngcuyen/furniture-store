package com.hutech.furniturestore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 350)
    private String name;

    private String description;

    private Double price;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    private Integer quantity;

    private Integer sold;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "is_best_seller")
    private Boolean isBestSeller = false;

    @Column(name = "is_removed")
    private Boolean isRemoved = false;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;
}
