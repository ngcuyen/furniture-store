package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.models.Review;
import com.hutech.furniturestore.sevices.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    /**
     * Description: Create a new review
     * Purpose: Adds a new review to the system.
     * Path: /api/reviews
     * Method: POST
     * Body: {user, product, comment, rating}
     */
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    /**
     * Description: Get a review by ID
     * Purpose: Retrieves a specific review by its ID.
     * Path: /api/reviews/{id}
     * Method: GET
     */
    @GetMapping("/{id}")
    public Review getReview(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    /**
     * Description: Get all reviews
     * Purpose: Retrieves all reviews in the system.
     * Path: /api/reviews
     * Method: GET
     */
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    /**
     * Description: Delete a review
     * Purpose: Deletes a specific review by its ID.
     * Path: /api/reviews/{id}
     * Method: DELETE
     */
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    /**
     * Description: Update a review
     * Purpose: Updates a specific review by its ID.
     * Path: /api/reviews/{id}
     * Method: PUT
     * Body: {user, product, comment, rating}
     */
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        Review review = reviewService.getReviewById(id);

        if (review != null) {
            review.setUser(reviewDetails.getUser());
            review.setProduct(reviewDetails.getProduct());
            review.setComment(reviewDetails.getComment());
            review.setNumberOfRatings(reviewDetails.getNumberOfRatings());
            return reviewService.saveReview(review);
        } else {
            return null; // Handle not found case
        }
    }
}
