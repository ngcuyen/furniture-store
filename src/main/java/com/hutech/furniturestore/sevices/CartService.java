package com.hutech.furniturestore.sevices;

import com.hutech.furniturestore.component.SecurityUtil;
import com.hutech.furniturestore.constants.CartResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.request.cart.CartItemDto;
import com.hutech.furniturestore.models.*;
import com.hutech.furniturestore.repositories.CartItemRepository;
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
    private CartItemRepository cartItemRepository;
    @Autowired
    private SecurityUtil securityUtil;
    @Transactional
    public CartResponse addToCart(CartItemDto cartItemRequest) {
        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + cartItemRequest.getProductId()));

        User currentUser = securityUtil.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart(currentUser);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemRequest.getProductId()))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem(product, cartItemRequest.getQuantity(), cart);
            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
        }

        cartRepository.save(cart);
        cartItemRepository.save(cartItem);

        return convertToCartResponse(cartItem);
    }
//    public List<CartItem> getCartItems() {
//        return cartItems;
//    }
//    public void removeFromCart(Long productId) {
//        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
//    }
    public void clearCart() {
        cartItems.clear();
    }



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
        Product product = cartItem.getProduct();
        CartResponse response = new CartResponse();
        response.setProductId(product.getId());
        response.setProductName(product.getName());
        response.setProductPrice(product.getPrice());
        response.setQuantity(cartItem.getQuantity());
        response.setThumbnail(product.getThumbnail()); // Assuming thumbnail is a field in Product entity

        return response;
    }
}
