package com.hutech.furniturestore.controllers;

import com.hutech.furniturestore.dtos.UserDto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    /**
     * Description: Login a user with email and password
     * Purpose: Authenticates the user and returns a token.
     * Path: /login
     * Method: POST
     * Body: { email: string, password: string }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        try {
            return ResponseEntity.ok("Login successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description. Register a new user
     * Purpose: Create a new user
     * Path: /register
     * Method: POST
     * Body: { user_name: string, email: string, password: string, confirm_password: string, phone: string }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        try {
            return ResponseEntity.ok("Register successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description. Logout a user
     * Purpose: Logs the user out and invalidates the token
     * Path: /logout
     * Method: POST
     * Header: { Authorization: Bearer <access_token> }
     * Body: { refresh_token: string }
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token,
                                    @Valid @RequestBody LogoutRequestDto logoutRequest) {
        try {
            return ResponseEntity.ok("Logout successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description. Verify otp when user client
     * Purpose: Authenticates the user using OTP
     * Path: /otp/authenticate
     * Method: POST
     * Body: { otp: string }
     */
    @PostMapping("/otp/authenticate")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpDto verifyOtpRequest) {
        try {
            return ResponseEntity.ok("Verify otp when user client successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    /**
     * Description. Resend otp when user client click on the button resend otp
     * Purpose:  Resends or revalidates the OTP for the user.
     * Path: /otp/revalidate
     * Method: POST
     * Body: { email: string }
     */
    @PostMapping("/otp/revalidate")
    public ResponseEntity<?> resendOtp(@Valid @RequestBody ResendOtpDto resendOtpRequest) {
        try {
            return ResponseEntity.ok("Resend otp successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description. Refresh Token
     * Purpose: Refreshes an expired token.
     * Path: /token/refresh
     * Method: POST
     * Body: { refresh_token: string }
     */
    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenRequest) {
        try {
            return ResponseEntity.ok("Refresh token successful");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description: Change password
     * Purpose: Allows the user to change their password.
     * Path: /password/change
     * Method: POST
     * Header: { Authorization: Bearer <access_token> }
     * Body: { old_password: string, password: string, confirm_password: string }
     */
    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordRequest) {
        try {
            return ResponseEntity.ok("Change-password successful");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description. Submit email to forgot password, send email to user
     * Purpose: Initiates the process to reset the forgotten password.
     * Path: /password/forgot
     * Method: POST
     * Body: { email: string }
     */
    @PostMapping("/password/forgot")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordRequest) {
        try {
            return ResponseEntity.ok("Forgot-password successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description: Reset password
     * Purpose: Resets the user's password.
     * Path: /password/reset
     * Method: POST
     * Body: { email: string, password: string, confirm_password: string }
     */
    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordRequest) {
        try {
            return ResponseEntity.ok("Reset password successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description: Get my profile
     * Purpose: Retrieves the personal information of the authenticated user.
     * Path: /@me/profile
     * Method: GET
     * Header: { Authorization: Bearer <access_token> }
     */
    @GetMapping("/@me/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok("Get my profile successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description: Update self profile
     * Purpose: Updates the personal information of the authenticated user.
     * Path: /@me/profile
     * Method: PUT
     * Body: {full_name: string, phone: string, gender: string, avatar: string, cover_photo: string, address: string}
     * Header: { Authorization: Bearer <access_token> }
     */
    @PutMapping("/@me/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileDto updateProfileRequest,
                                           @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok("Update profile successful");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description: Upload avatar
     * Purpose: Changes the avatar for the authenticated user.
     * Path: /@me/avatar
     * Method: POST
     * Body: {image: file}
     * Header: { Authorization: Bearer <access_token> }
     */
    @PostMapping("/@me/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file,
                                          @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok("Upload avatar successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Description: Upload thumbnail
     * Purpose: Changes the cover photo for the authenticated user.
     * Path: /@me/thumbnail
     * Method: POST
     * Body: {image: file}
     * Header: { Authorization: Bearer <access_token> }
     */
    @PostMapping("/@me/thumbnail")
    public ResponseEntity<?> uploadThumbnailUser(@RequestParam("file") MultipartFile file,
                                                 @RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok("Upload thumbnail successfully");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
