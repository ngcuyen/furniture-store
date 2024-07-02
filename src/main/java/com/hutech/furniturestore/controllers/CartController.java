package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.constants.CartResponse;
import com.hutech.furniturestore.constants.CategoryResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.request.cart.CartItemDto;
import com.hutech.furniturestore.repositories.CartRepository;
import com.hutech.furniturestore.sevices.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(@Valid @RequestBody CartItemDto cartItemRequest) {
        CartResponse cartResponse = cartService.addToCart(cartItemRequest);
        ApiResponse<CartResponse> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Add cart successfully",
                cartResponse,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Void> updateCart(@PathVariable Long id, @RequestBody CartItemDto cartItemRequest) {
//        cartService.updateCart(id, cartItemRequest.getProductId(), cartItemRequest.getQuantity());
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
//        cartService.removeFromCart(id);
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PaginationResponse<CartResponse>>> getAllCartsPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort_by,
            @RequestParam(defaultValue = "asc") String sort_order
    ) {

            PaginationResponse<CartResponse> categoriesPage = cartService.getAllCartsPagination(page, size, sort_by, sort_order);
            ApiResponse<PaginationResponse<CartResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Successfully retrieved all cart items in database.",
                    categoriesPage,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<List<CartItem>> getCartByUserId(@PathVariable Long userId) {
//        List<CartItem> cartItems = cartService.getCartByUserId(userId);
//        return ResponseEntity.ok(cartItems);
//    }
}
