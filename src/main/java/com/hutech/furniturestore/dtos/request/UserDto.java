package com.hutech.furniturestore.dtos.request;

import lombok.*;

public class UserDto {
    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequestDto {
        private String email;
        private String password;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequestDto {
        private String username;
        private String password;
        private String email;
        private String phone;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogoutRequestDto {
        private String refresh_token;
    }


    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VerifyOtpDto {
        private String username;
        private String otp;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResendOtpDto {
        private String email;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshTokenDto {
        private String email;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangePasswordDto {
        private String oldPassword;
        private String newPassword;
        private String confirmPassword;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ForgotPasswordDto {
        private String email;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResetPasswordDto {
        private String email;
        private String password;
        private String confirmPassword;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateProfileDto {
        private String fullName;
        private String phone;
        private String gender;
        private String avatar;
        private String thumbnail;
        private String address;
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserProfileDto {
        private String fullName;
        private String phone;
        private String gender;
        private String avatar;
        private String thumbnail;
        private String address;
    }
}
