package com.ktpm.potatoapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // GLOBAL ERROR
    UNCATEGORIZED(9999, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    MESSAGE_KEY_INVALID(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1002, "Authentication is required", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1003, "You do not have permission", HttpStatus.FORBIDDEN),
    JSON_INVALID(1004, "Invalid JSON request", HttpStatus.BAD_REQUEST),

    // CATEGORY ERROR
    CATEGORY_EXISTED(2001, "Category already existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(2002, "Category not existed", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_BLANK(2003, "Category name is required", HttpStatus.BAD_REQUEST),

    //  USER ERROR
    USER_EXISTED(3001, "User already existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(3002, "User not existed", HttpStatus.NOT_FOUND),
    USER_EMAIL_BLANK(3003, "Email is required", HttpStatus.BAD_REQUEST),
    USER_EMAIL_INVALID(3004, "Email is not well-formed", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_BLANK(3005, "Password is required", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_INVALID_SIZE(
            3006,
            "Password must be between {min} and {max} characters",
            HttpStatus.BAD_REQUEST
    ),
    USER_PASSWORD_INVALID_PATTERN(
            3007,
            "Password must contain at least one uppercase, one lowercase, one digit, and one special character",
            HttpStatus.BAD_REQUEST
    ),
    USER_FULLNAME_BLANK(3008, "Full name is required", HttpStatus.BAD_REQUEST),

    // MERCHANT ERROR,
    MERCHANT_EXISTED(4001, "Merchant already existed", HttpStatus.BAD_REQUEST),
    MERCHANT_NOT_FOUND(4002, "Merchant not existed", HttpStatus.NOT_FOUND),
    MERCHANT_NAME_BLANK(4003, "Merchant name is required", HttpStatus.BAD_REQUEST),
    MERCHANT_INACTIVE(4004, "Merchant is inactive", HttpStatus.BAD_REQUEST);

    private final Integer code;
    private final String message;
    private final HttpStatusCode statusCode;
}
