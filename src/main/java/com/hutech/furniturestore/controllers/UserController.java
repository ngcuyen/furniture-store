package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.constants.PaginationResponse;
import com.hutech.furniturestore.dtos.request.auth.DeleteUsersDto;
import com.hutech.furniturestore.dtos.request.auth.UserSignInRequest;
import com.hutech.furniturestore.dtos.request.auth.UserUpdateRequest;
import com.hutech.furniturestore.dtos.response.user.SignupResponse;
import com.hutech.furniturestore.dtos.response.user.UserResponse;
import com.hutech.furniturestore.mapper.UserMapper;
import com.hutech.furniturestore.models.User;
import com.hutech.furniturestore.sevices.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;
    UserMapper userMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");

    /**
     * Description. Register a new user
     * Purpose: Create a new user
     * Path: api/v1/users/register
     * Method: POST
     * Body: { username: String, email: String, password: String, firstName: string, lastName: String, phone: String }
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserSignInRequest userDTO) {
        User user = userService.createUser(userDTO);
        SignupResponse result = new SignupResponse();
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setEmail(user.getEmail());
        ApiResponse<SignupResponse> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Create account successfully",
                result,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Description: Get all users
     * Purpose: Get all users.
     * Path: /api/v1/users
     * Method: GET
     * Header: { Authorization: Bearer <access_token> }
     */
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("email: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Successfully retrieved all users in database.",
                users,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Description: Get all user with pagination
     * Purpose: Get all user with pagination.
     * Path: /api/v1/users/pagination
     * Method: GET
     * Header: { Authorization: Bearer <access_token> }
     * Body: {page: Int, size: Int, sort_by: String, sort_order: String}
     */
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PaginationResponse<UserResponse>>> getAllRolesPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort_by,
            @RequestParam(defaultValue = "asc") String sort_order) {
        PaginationResponse<UserResponse> userPage = userService.getAllUserPagination(page, size, sort_by, sort_order);
        ApiResponse<PaginationResponse<UserResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Successfully retrieved all user in database.",
                userPage,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Description: Retrieves the current user level for a specific user, identified by ID.
     * Purpose: Get a user by id
     * Path: /api/v1/users/:id
     * Method: GET
     * Header: { Authorization: Bearer <access_token> }
     * Params: { id: Long }
     */
    @GetMapping("/{id}")
    public  ResponseEntity<ApiResponse<?>> getUserById(@PathVariable String id) {
        Optional<User> userResponse = userService.getUserById(id);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User retrieved successfully",
                userResponse,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Description: Update user information by its ID.
     * Purpose: Update user information by user_id
     * Path: /api/v1/users/:id
     * Method: PUT
     * Header: { Authorization: Bearer <access_token> }
     * Body: { firsName: String, lastName: String, phone: String, address: String, avatar: String, thumbnail: String }
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateRequest userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        UserResponse result = userMapper.toUserResponse(updatedUser);
        if (updatedUser != null) {
            ApiResponse<UserResponse> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "User updated successfully",
                    result,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Description: Delete a specific user by its ID.
     * Purpose: Delete a user by ID
     * Path: /api/v1/users/:id
     * Method: DELETE
     * Header: { Authorization: Bearer <access_token> }
     * Params: { id: Long }
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User deleted successfully",
                null,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Description: Delete multiple user by its ID.
     * Purpose: Delete multiple user by list user ID
     * Path: /api/v1/users
     * Method: DELETE
     * Header: { Authorization: Bearer <access_token> }
     * Body: [ id: Long ]
     */
    @DeleteMapping("")
    public ResponseEntity<ApiResponse<Void>> deleteUsers(@RequestBody DeleteUsersDto deleteUsersRequest) {
        userService.deleteUsers(deleteUsersRequest);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Users deleted multiple successfully",
                null,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
