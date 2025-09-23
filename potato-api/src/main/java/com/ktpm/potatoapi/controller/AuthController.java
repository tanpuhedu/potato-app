package com.ktpm.potatoapi.controller;

import com.ktpm.potatoapi.payload.request.UserLogInRequest;
import com.ktpm.potatoapi.payload.request.UserSignUpRequest;
import com.ktpm.potatoapi.payload.response.ApiResponse;
import com.ktpm.potatoapi.payload.response.UserLogInResponse;
import com.ktpm.potatoapi.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that provides user authentication APIs.
 * <p>
 * This controller exposes endpoints under {@code /api/auth} for:
 * <ul>
 *     <li>User registration (Sign up)</li>
 *     <li>User authentication (Log in)</li>
 * </ul>
 *
 * It delegates user-related business logic to the {@link UserService}.
 * @author Hieu
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication APIs", description = "APIs for signup and login")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "*")
public class AuthController {

    UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "Sign up", description = "API for registered users")
    public ResponseEntity<?> signup(@RequestBody @Valid UserSignUpRequest userSignUpRequest, HttpServletRequest httpRequest) {
        ApiResponse<UserLogInResponse> response = ApiResponse.<UserLogInResponse>builder()
                .data(userService.signUp(userSignUpRequest, httpRequest))
                .message("Sign up successful")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Log in", description = "API for users login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLogInRequest userLogInRequest, HttpServletRequest httpRequest) {
        ApiResponse<UserLogInResponse> response = ApiResponse.<UserLogInResponse>builder()
                .data(userService.logIn(userLogInRequest, httpRequest))
                .message("Log in successful")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
