package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.dtos.RoleDto;
import com.hutech.furniturestore.exceptions.NoSuchElementFoundException;
import com.hutech.furniturestore.models.Role;
import com.hutech.furniturestore.sevices.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Validated
@Tag(name = "roles", description = "Role management APIs")
public class RoleController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Description: Get all roles with pagination
     * Purpose: Get all roles with pagination.
     * Path: /api/v1/roles
     * Method: GET
     * Header: { Authorization: Bearer <access_token> }
     * Body: { page: int, limit: int, sort: String, sort_order: String }
     */
    @GetMapping
    @Operation(
            summary = "Get all roles with pagination",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Roles retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Roles retrieved successfully\", \"data\": { \"content\": [ { \"id\": 1, \"name\": \"Admin\", \"description\": \"Administrator role\" }, { \"id\": 2, \"name\": \"User\", \"description\": \"User role\" } ], \"pageable\": \"INSTANCE\", \"last\": true, \"totalPages\": 1, \"totalElements\": 2, \"size\": 20, \"number\": 0, \"sort\": { \"sorted\": false, \"unsorted\": true, \"empty\": true }, \"first\": true, \"numberOfElements\": 2, \"empty\": false }, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
                                            value = "{ \"statusCode\": 500, \"message\": \"An unexpected error occurred\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<Page<RoleDto>>> getAllRoles(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        try {
            ApiResponse<Page<RoleDto>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Roles retrieved successfully",
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<Page<RoleDto>> response = new ApiResponse<>(
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
     * Description: Allows the creation of a new role in the catalog.
     * Purpose: Create a new role
     * Path: /api/v1/roles
     * Method: POST
     * Header: { Authorization: Bearer <access_token> }
     * Body: { name: String, description: String }
     */
    @PostMapping
    @Operation(
            summary = "Create a new role",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Role created successfully",
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
    public ResponseEntity<ApiResponse<RoleDto>> createRole(@Valid @RequestBody RoleDto roleDto) {
        try {
            ApiResponse<RoleDto> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Role created successfully",
                    roleDto,
                    LocalDateTime.now().format(formatter),
                    null
            );
            Role newRole = new Role();
            newRole.setName(roleDto.getName());
            newRole.setDescription(roleDto.getDescription());
            roleService.saveRole(newRole);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            // Catch any unexpected exceptions and return a 500 status code
            ApiResponse<RoleDto> response = new ApiResponse<>(
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
     * Description: Retrieves the current roles level for a specific user, identified by roles.
     * Purpose: Get a role by id
     * Path: /api/v1/roles/:id
     * Method: GET
     * Header: { Authorization: Bearer <access_token> }
     * Params: { id: Long }
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a role by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Role retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Role retrieved successfully\", \"data\": { \"name\": \"Admin\", \"description\": \"Administrator role\" }, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
    public ResponseEntity<ApiResponse<RoleDto>> getRoleById(@PathVariable("id") Long id) {
        try {
            ApiResponse<RoleDto> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Role retrieved successfully",
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementFoundException ex) {
            ApiResponse<RoleDto> response = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    null,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ApiResponse<RoleDto> response = new ApiResponse<>(
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
     * Description: Delete a specific role by its ID.
     * Purpose: Delete a role by ID
     * Path: /api/v1/roles/:id
     * Method: DELETE
     * Header: { Authorization: Bearer <access_token> }
     * Params: { id: Long }
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a role by ID",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Role deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Role deleted successfully\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
    public ResponseEntity<ApiResponse<Void>> deleteRoleById(@PathVariable("id") Long id) {
        try {
            ApiResponse<Void> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Role deleted successfully",
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
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ApiResponse<Void> response = new ApiResponse<>(
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
     * Description: Delete multiple  role by its ID.
     * Purpose: Delete  multiple role by list role ID
     * Path: /api/v1/roles
     * Method: DELETE
     * Header: { Authorization: Bearer <access_token> }
     * Body: [ id: Long ]
     */
    @DeleteMapping
    @Operation(
            summary = "Delete multiple roles by IDs",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Roles deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 200, \"message\": \"Roles deleted successfully\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
                            description = "One or more roles not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{ \"statusCode\": 404, \"message\": \"One or more roles not found\", \"data\": null, \"dateTime\": \"01-01-2023T12:00:00.000\", \"messageConstants\": null }"
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
    public ResponseEntity<ApiResponse<Void>> deleteRolesByIds(@RequestBody List<Long> ids) {
        try {
            ApiResponse<Void> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Roles deleted successfully",
                    null,
                    LocalDateTime.now().format(formatter),
                    "Operation successful"
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
