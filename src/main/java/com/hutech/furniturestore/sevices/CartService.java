package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.component.SecurityUtil;
import com.hutech.furniturestore.constants.CartResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.request.cart.CartItemDto;
import com.hutech.furniturestore.models.*;
import com.hutech.furniturestore.repositories.CartRepository;
import com.hutech.furniturestore.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SecurityUtil securityUtil;
    public CartResponse addToCart(CartItemDto cartItemRequest) {
       try {
           Product product = productRepository.findById(cartItemRequest.getProductId())
                   .orElseThrow(() -> new IllegalArgumentException("Product not found: " + cartItemRequest.getProductId()));

           User currentUser = securityUtil.getCurrentUser();
           Cart cart = cartRepository.findByUserId("cbc3eb6d-adba-4490-90a9-9121d1bf1fc5")
                   .orElseGet(() -> {
                       Cart newCart = new Cart(currentUser, new ArrayList<>());
                       return cartRepository.save(newCart);
                   });



           CartItem cartItem = new CartItem();
           cartItem.setProduct(product);
           cartItem.setQuantity(cartItemRequest.getQuantity());
           cartItem.setCart(cart);

           cart.getCartItems().add(cartItem);
           cartRepository.save(cart);

           return convertToCartResponse(cartItem);
       }catch (Exception e) {
           // Log exception for debugging purposes
           log.info("Error adding product to cart: " + e.getMessage(), e);
           // Throw a custom exception or handle it appropriately
           throw new RuntimeException("Failed to add product to cart. Please try again later.");
       }
    }
//    public List<CartItem> getCartItems() {
//        return cartItems;
//    }
//    public void removeFromCart(Long productId) {
//        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
//    }
//    public void clearCart() {
//        cartItems.clear();
//    }

    public PaginationResponse<CartResponse> getAllCartsPagination(int page, int size, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<Cart> cartPage = cartRepository.findAll(pageable);
        List<CartResponse> cartResponses = cartPage.getContent().stream()
                .flatMap(cart -> cart.getCartItems().stream().map(this::convertToCartResponse))
                .collect(Collectors.toList());

        PaginationResponse<CartResponse> paginatedResponse = new PaginationResponse<>();
        paginatedResponse.setItems(cartResponses);
        paginatedResponse.setPage(cartPage.getNumber() + 1);
        paginatedResponse.setPerPage(cartPage.getSize());
        paginatedResponse.setTotalPages(cartPage.getTotalPages());
        paginatedResponse.setTotalItems(cartPage.getTotalElements());

        return paginatedResponse;
    }

    private CartResponse convertToCartResponse(CartItem cartItem) {
        return new CartResponse(
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity()
        );
    }
}
