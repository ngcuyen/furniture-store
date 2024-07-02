package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.constants.CategoryResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.request.CategoryDto;
import com.hutech.furniturestore.exceptions.NoSuchElementFoundException;
import com.hutech.furniturestore.sevices.CategoryService;
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
@RequestMapping("/categories")
@Tag(name="categories",description = "Category management APIs")
public class CategoryController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Description: Get all categories
     * Purpose: Get all categories with pagination.
     * Path: /api/v1/categories
     * Method: GET
     */
    @GetMapping("/pagination")
    @Operation(
            summary = "Get all categories with pagination",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Categories retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Successfully retrieved all categories in database.\", " +
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
    public ResponseEntity<ApiResponse<PaginationResponse<CategoryResponse>>> getAllCategoriesPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort_by,
            @RequestParam(defaultValue = "asc") String sort_order
    ) {
        try {
            PaginationResponse<CategoryResponse> categoriesPage = categoryService.getAllCategoriesPagination(page, size, sort_by, sort_order);
            ApiResponse<PaginationResponse<CategoryResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Successfully retrieved all roles in database.",
                    categoriesPage,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<PaginationResponse<CategoryResponse>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred",
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Description: Retrieves the current category identified by category's id.
     * Purpose: Get a category by id
     * Path: /api/v1/categories/:id
     * Method: GET
     * Params: { id: Long }
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a category by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Category retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Category retrieved successfully\", \"data\": { \"name\": \"Admin\", \"description\": \"Administrator role\" }, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable("id") Long id) {
        try {
            CategoryResponse categoryResponse = categoryService.getCategoryById(id);
            ApiResponse<CategoryResponse> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Category retrieved successfully",
                    categoryResponse,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementFoundException ex) {
            ApiResponse<CategoryResponse> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ApiResponse<CategoryResponse> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred",
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * Description: Allows the creation of a new category in the catalog.
     * Purpose: Create a new category
     * Path: /api/v1/categories
     * Method: POST
     * Header: { Authorization: Bearer <access_token> }
     * Body: { name: String, description: String }
     */
    @PostMapping
    @Operation(
            summary = "Create a new category",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Category created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 201, \"message\": \"Category created successfully\", \"data\": { \"name\": \"Admin\", \"description\": \"Administrator role\" }, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        try {
            CategoryResponse categoryResponse = categoryService.createCategory(categoryDto);
            ApiResponse<CategoryResponse> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Category created successfully",
                    categoryResponse,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            // Catch any unexpected exceptions and return a 500 status code
            ApiResponse<CategoryResponse> response = new ApiResponse<>(
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
     * Description: Delete multiple  category by its ID.
     * Purpose: Delete  category by its ID
     * Path: /api/v1/categories/:id
     * Method: PUT
     * Header: { Authorization: Bearer <access_token> }
     * Body: [ name: String, description: String]
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update category by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Category updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 201, \"message\": \"Category updated successfully\", \"data\": { \"name\": \"Admin\", \"description\": \"Administrator role\" }, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) {
        try {
            CategoryResponse updatedCategory = categoryService.updateCategory(id, categoryDto);
            ApiResponse<CategoryResponse> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Category updated successfully",
                    updatedCategory,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementFoundException ex) {
            ApiResponse<CategoryResponse> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    null,
                    LocalDateTime.now().format(formatter),
                    "Element not found"
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ApiResponse<CategoryResponse> response = new ApiResponse<>(
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
     * Description: Delete a specific category by its ID.
     * Purpose: Delete a category by ID
     * Path: /api/v1/categories/:id
     * Method: DELETE
     * Header: { Authorization: Bearer <access_token> }
     * Params: { id: Long }
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a category by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Category deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Category deleted successfully\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
    public ResponseEntity<ApiResponse<Void>> deleteCategoryById(@PathVariable("id") Long id) {
        try {
            categoryService.deleteCategoryById(id);
            ApiResponse<Void> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Category deleted successfully",
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
