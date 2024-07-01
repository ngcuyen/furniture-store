package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.models.WishList;
import com.hutech.furniturestore.sevices.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    /**
     * Description: Create a new wishlist
     * Purpose: Adds a new wishlist to the system.
     * Path: /api/wishlists
     * Method: POST
     * Body: {user, products}
     */
    @PostMapping
    public WishList createWishlist(@RequestBody WishList wishlist) {
        return wishlistService.saveWishlist(wishlist);
    }

    /**
     * Description: Get a wishlist by ID
     * Purpose: Retrieves a specific wishlist by its ID.
     * Path: /api/wishlists/{id}
     * Method: GET
     */
    @GetMapping("/{id}")
    public WishList getWishlist(@PathVariable Long id) {
        return wishlistService.getWishlistById(id);
    }

    /**
     * Description: Get all wishlists
     * Purpose: Retrieves all wishlists in the system.
     * Path: /api/wishlists
     * Method: GET
     */
    @GetMapping
    public List<WishList> getAllWishlists() {
        return wishlistService.getAllWishlists();
    }

    /**
     * Description: Delete a wishlist
     * Purpose: Deletes a specific wishlist by its ID.
     * Path: /api/wishlists/{id}
     * Method: DELETE
     */
    @DeleteMapping("/{id}")
    public void deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
    }

    /**
     * Description: Update a wishlist
     * Purpose: Updates a specific wishlist by its ID.
     * Path: /api/wishlists/{id}
     * Method: PUT
     * Body: {user, products}
     */
    @PutMapping("/{id}")
    public WishList updateWishlist(@PathVariable Long id, @RequestBody WishList wishlistDetails) {
        WishList wishlist = wishlistService.getWishlistById(id);

        if (wishlist != null) {
            wishlist.setUser(wishlistDetails.getUser());
            wishlist.setProduct(wishlistDetails.getProduct());
            return wishlistService.saveWishlist(wishlist);
        } else {
            return null; // Handle not found case
        }
    }
}
