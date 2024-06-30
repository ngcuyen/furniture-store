package com.hutech.furniturestore.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public enum ErrorCode {
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST, null),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST, null),
    EMAIL_REQUIRED(1003, "Email is required", HttpStatus.BAD_REQUEST, null),
    EMAIL_INVALID(1004, "Email should be valid", HttpStatus.BAD_REQUEST, null),
    USERNAME_INVALID(1005, "Username must be between 3 and 50 characters", HttpStatus.BAD_REQUEST, null),
    INCORRECT_FORMAT_PASSWORD(1006, "Incorrect password format", HttpStatus.BAD_REQUEST, null),
    FIRSTNAME_REQUIRED(1007, "First name is required", HttpStatus.BAD_REQUEST, null),
    FIRSTNAME_INVALID(1008, "First name is invalid", HttpStatus.BAD_REQUEST, null),
    LASTNAME_REQUIRED(1009, "Last name is required", HttpStatus.BAD_REQUEST, null),
    LASTNAME_INVALID(1010, "Last name is invalid", HttpStatus.BAD_REQUEST, null),
    PASSWORD_REQUIRED(1011, "Password is required", HttpStatus.BAD_REQUEST, null),
    PASSWORD_LENGTH_MUST_BE_FROM_8_TO_16(1012, "Password must be between 8 and 16 characters", HttpStatus.BAD_REQUEST, null),
    PHONE_REQUIRED(1013, "Phone number is required", HttpStatus.BAD_REQUEST, null),
    PHONE_INVALID(1014, "Phone number is invalid", HttpStatus.BAD_REQUEST, null),
    USER_NOT_EXISTED(1015, "User does not exist or user has been removed", HttpStatus.NOT_FOUND, null),
    UNAUTHENTICATED(1016, "Unauthenticated", HttpStatus.UNAUTHORIZED, null),
    UNAUTHORIZED(1017, "You do not have permission", HttpStatus.FORBIDDEN, null),
    VALID_THUMBNAIL_URL_IMAGE(1018, "Thumbnail url must be a valid image URL and have a valid image extension.", HttpStatus.BAD_REQUEST, null),
    VALID_AVATAR_URL_IMAGE(1019, "Avatar url must be a valid image URL and have a valid image extension.", HttpStatus.BAD_REQUEST, null),
    USER_NOT_FOUND(1020, "User id does not exist or user has been removed", HttpStatus.NOT_FOUND, null),
    INVALID_TOKEN(1021, "Invalid token", HttpStatus.UNAUTHORIZED, null),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR, "System error"),;

    ErrorCode(int code, String message, HttpStatusCode statusCode, String messageConstants) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ssSSS"));
        this.messageConstants = messageConstants;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
    private String dateTime;
    private String messageConstants;
}
