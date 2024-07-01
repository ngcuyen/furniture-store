package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.constants.ProductResponse;
import com.hutech.furniturestore.dtos.ProductDto;
import com.hutech.furniturestore.exceptions.NoSuchElementFoundException;
import com.hutech.furniturestore.sevices.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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
    @Operation(
            summary = "Get all products with pagination",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Products retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Successfully retrieved all products in database.\", " +
                                                    "\"data\": { \"items\": [ { \"id\": 1, \"name\": \"Product 1\", \"description\": \"Product description\" } ]}, " +
                                                    "\"dateTime\": \"18-06-2024T01:36:12.822\", " +
                                                    "\"messageConstants\": null}"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 401, \"message\": \"Unauthorized access\", " +
                                                    "\"data\": null, " +
                                                    "\"dateTime\": \"01-01-2023T12:00:00.000\", " +
                                                    "\"messageConstants\": null}"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 403, \"message\": \"Access forbidden\"," +
                                                    " \"data\": null, " +
                                                    "\"dateTime\": \"01-01-2023T12:00:00.000\", " +
                                                    "\"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 500, \"message\": \"An unexpected error occurred\", " +
                                                    "\"data\": null, " +
                                                    "\"dateTime\": \"01-01-2023T12:00:00.000\", " +
                                                    "\"messageConstants\": null }"
                                    )
                            )
                    )
            }
    )
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
    @Operation(
            summary = "Get a product by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Product retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Product retrieved successfully\", \"data\": { \"name\": \"Admin\", \"description\": \"Administrator role\" }, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 401, \"message\": \"Unauthorized access\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 403, \"message\": \"Access forbidden\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Role not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 404, \"message\": \"Role not found\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 500, \"message\": \"An unexpected error occurred\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    )
            }
    )
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
    @Operation(
            summary = "Create a new product",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Product created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 201, \"message\": \"Role created successfully\", \"data\": { \"name\": \"Admin\", \"description\": \"Administrator role\" }, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 401, \"message\": \"Unauthorized access\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null}"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 403, \"message\": \"Access forbidden\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 500, \"message\": \"An unexpected error occurred\", \"data\": null, \"dateTime\": \"01-01-2024T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductDto createProductDto) {
        try {
            ProductResponse productResponse = productService.createProduct(createProductDto);
            ApiResponse<ProductResponse> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Product created successfully",
                    productResponse,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            // Catch any unexpected exceptions and return a 500 status code
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
     * Description: Delete multiple  role by its ID.
     * Purpose: Delete  multiple role by list role ID
     * Path: /api/v1/roles/:id
     * Method: PUT
     * Header: { Authorization: Bearer <access_token> }
     * Body: [ name: String, description: String]
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update product by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Product updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 201, \"message\": \"Product updated successfully\", \"data\": { \"name\": \"Admin\", \"description\": \"Administrator role\" }, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 403, \"message\": \"Access forbidden\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Product not found or has been removed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 404, \"message\": \"Role not found or has been removed with ID: 1\", \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": \"Element not found\" }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 500, \"message\": \"An unexpected error occurred\", \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": \"System error\" }"
                                    )
                            )
                    )
            }
    )
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
    @Operation(
            summary = "Delete a product by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Product deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Product deleted successfully\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 401, \"message\": \"Unauthorized access\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null}"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 403, \"message\": \"Access forbidden\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Role not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 404, \"message\": \"Role not found\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 500, \"message\": \"An unexpected error occurred\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    )
            }
    )
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
