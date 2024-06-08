package com.hutech.furniturestore.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<?> getAllCategories(@RequestParam("page") int page, @RequestParam("limit") int limit){
        return ResponseEntity.ok("Get all categories");
    }
}
