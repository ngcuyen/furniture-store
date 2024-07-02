package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.constants.ApiResponse;
import com.hutech.furniturestore.dtos.request.auth.UserLoginRequest;
import com.hutech.furniturestore.dtos.request.auth.VerifyTokenRequest;
import com.hutech.furniturestore.dtos.response.user.UserLoginResponse;
import com.hutech.furniturestore.dtos.response.user.VerifyTokenResponse;
import com.hutech.furniturestore.repositories.UserRepository;
import com.hutech.furniturestore.sevices.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    UserRepository userRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS");
    /**
     * Description: Login a user with email and password
     * Purpose: Authenticates the user and returns a token.
     * Path: /api/v1/auth/login
     * Method: POST
     * Body: { email: String, password: String }
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponse>> loginUser(@RequestBody @Valid UserLoginRequest request) {
        var userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            UserLoginResponse result = authenticationService.authenticate(request);
            ApiResponse<UserLoginResponse> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Login successfully!",
                    result,
                    LocalDateTime.now().format(formatter),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ApiResponse<UserLoginResponse> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Email or password is incorrect",
                null,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/token/verify")
    public ResponseEntity<ApiResponse<VerifyTokenResponse>> authenticate(@RequestBody VerifyTokenRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        ApiResponse<VerifyTokenResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                null,
                result,
                LocalDateTime.now().format(formatter),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
