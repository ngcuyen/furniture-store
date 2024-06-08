package com.hutech.furniturestore.sevices;



import com.hutech.furniturestore.models.WishList;
import com.hutech.furniturestore.repositories.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WishlistService {
    @Autowired
    private WishListRepository wishlistRepository;

    public WishList saveWishlist(WishList wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public WishList getWishlistById(Long id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    public List<WishList> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public void deleteWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }
}
