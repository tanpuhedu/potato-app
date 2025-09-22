package com.ktpm.potatoapi.controller;

import com.ktpm.potatoapi.dto.request.UserRequest;
import com.ktpm.potatoapi.dto.response.ApiResponse;
import com.ktpm.potatoapi.dto.response.UserResponse;
import com.ktpm.potatoapi.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping("/admin/users")
    @Operation(summary = "get list of users")
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAll())
                .message("get list of users successfully")
                .build();
    }

    @GetMapping("/admin/users/{id}")
    @Operation(summary = "get user by id")
    public ApiResponse<UserResponse> getById(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getById(id))
                .message("get user successfully")
                .build();
    }

    @PostMapping("/admin/users")
    @Operation(summary = "create new user")
    public ApiResponse<UserResponse> create(@RequestBody @Valid UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createCustomer(request))
                .message("create user successfully")
                .build();
    }

    @PutMapping("/admin/users/{id}")
    @Operation(summary = "update user by id")
    public ApiResponse<UserResponse> update(@PathVariable Long id,
                                            @RequestBody @Valid UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.update(id, request))
                .message("update user successfully")
                .build();
    }

    @PutMapping("/admin/users/{id}")
    @Operation(summary = "update user status by id")
    public ApiResponse<UserResponse> updateStatus(@PathVariable Long id, @RequestParam int status) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateStatus(id, status))
                .message("update user successfully")
                .build();
    }
}
