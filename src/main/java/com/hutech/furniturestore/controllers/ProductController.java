package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.constants.ProductResponse;
import com.hutech.furniturestore.dtos.ProductDto;
import com.hutech.furniturestore.exceptions.NoSuchElementFoundException;
import com.hutech.furniturestore.sevices.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/products")
@Tag(name="products",description = "Product management APIs")
public class ProductController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Description: Get all products
     * Purpose: Get all products with pagination.
     * Path: /api/v1/products
     * Method: GET
     */
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PaginationResponse<ProductResponse>>> getAllProductsPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort_by,
            @RequestParam(defaultValue = "asc") String sort_order
    ) {

            PaginationResponse<ProductResponse> productsPage = productService.getAllProductsPagination(page, size, sort_by, sort_order);
            ApiResponse<PaginationResponse<ProductResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Successfully retrieved all products in database.",
                    productsPage,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

    }


    /**
     * Description: Retrieves the current product identified by product's id.
     * Purpose: Get a product by id
     * Path: /api/v1/products/:id
     * Method: GET
     * Params: { id: Long }
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("id") Long id) {
        try {
            ProductResponse roleResponse = productService.getProductById(id);
            ApiResponse<ProductResponse> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Product retrieved successfully",
                    roleResponse,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementFoundException ex) {
            ApiResponse<ProductResponse> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * Description: Allows the creation of a new product in the catalog.
     * Purpose: Create a new product
     * Path: /api/v1/products
     * Method: POST
     * Header: { Authorization: Bearer <access_token> }
     * Body: { name: String, description: String }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductDto createProductDto) {
            ProductResponse productResponse = productService.createProduct(createProductDto);
            ApiResponse<ProductResponse> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Product created successfully",
                    productResponse,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Description: Delete multiple  role by its ID.
     * Purpose: Delete  multiple role by list role ID
     * Path: /api/v1/roles/:id
     * Method: PUT
     * Header: { Authorization: Bearer <access_token> }
     * Body: [ name: String, description: String]
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productUpdateRequest) {
        try {
            ProductResponse updatedProduct = productService.updateProduct(id, productUpdateRequest);
            ApiResponse<ProductResponse> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Product updated successfully",
                    updatedProduct,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementFoundException ex) {
            ApiResponse<ProductResponse> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    null,
                    LocalDateTime.now().format(formatter),
                    "Element not found"
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ApiResponse<ProductResponse> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred",
                    null,
                    LocalDateTime.now().format(formatter),
                    "System error"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Description: Delete a specific product by its ID.
     * Purpose: Delete a product by ID
     * Path: /api/v1/products/:id
     * Method: DELETE
     * Header: { Authorization: Bearer <access_token> }
     * Params: { id: Long }
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable("id") Long id) {
        try {
            productService.deleteProductById(id);
            ApiResponse<Void> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Product deleted successfully",
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementFoundException ex) {
            ApiResponse<Void> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    null,
                    LocalDateTime.now().format(formatter),
                    "Element not found"
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ApiResponse<Void> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred",
                    null,
                    LocalDateTime.now().format(formatter),
                    "System error"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
