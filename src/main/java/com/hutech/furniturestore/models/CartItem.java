package com.hutech.furniturestore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart_items")
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartItem(Product product, Integer quantity, Cart cart) {
        this.product = product;
        this.quantity = quantity;
        this.cart = cart;
    }
}
