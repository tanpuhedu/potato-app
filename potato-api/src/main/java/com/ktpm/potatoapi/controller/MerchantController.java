package com.ktpm.potatoapi.controller;

import com.ktpm.potatoapi.dto.request.MerchantRegistrationRequest;
import com.ktpm.potatoapi.dto.request.MerchantUpdateRequest;
import com.ktpm.potatoapi.dto.response.ApiResponse;
import com.ktpm.potatoapi.dto.response.MerchantRegistrationResponse;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.enums.EntityStatus;
import com.ktpm.potatoapi.service.merchant.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Merchant APIs", description = "APIs for merchant")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantController {
    MerchantService merchantService;

    @PostMapping("/admin/registered-merchants/{id}/approve")
    @Operation(summary = "Approve a registered merchant",
            description = "API for System Admin to approve a registered merchant")
    public ResponseEntity<?> approveMerchant(@PathVariable Long id) {
        ApiResponse<MerchantResponse> response = ApiResponse.<MerchantResponse>builder()
                .message("Registration request has been approved")
                .data(merchantService.approveMerchant(id))
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/admin/registered-merchants")
    @Operation(summary = "Show all merchants in system",
            description = "API for System Admin to retrieve a list of all merchants")
    public ResponseEntity<?> getAllRegisteredMerchant() {
        ApiResponse<List<MerchantRegistrationResponse>> response =
                ApiResponse.<List<MerchantRegistrationResponse>>builder()
                .data(merchantService.getAllRegisteredMerchant())
                .message("Get list of all registered merchants successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/admin/merchants")
    @Operation(summary = "Show all merchants in system",
            description = "API for System Admin to retrieve a list of all merchants")
    public ResponseEntity<?> getAllMerchant() {
        ApiResponse<List<MerchantResponse>> response = ApiResponse.<List<MerchantResponse>>builder()
                .data(merchantService.getAllMerchant())
                .message("Get list of all merchants successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/admin/merchants/{id}")
    @Operation(summary = "Show a merchant in system",
            description = "API for System Admin to retrieve a specific merchant")
    public ResponseEntity<?> getMerchantById(@PathVariable Long id) {
        ApiResponse<MerchantResponse> response = ApiResponse.<MerchantResponse>builder()
                .data(merchantService.getById(id))
                .message("Get merchant successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/admin/merchants/{id}/status")
    @Operation(summary = "Change a merchant's status",
            description = "API for System Admin to activate/deactivate a merchant")
    public ResponseEntity<?> changeMerchantStatus(
            @PathVariable Long id,
            @RequestParam EntityStatus status
    ) {
        ApiResponse<MerchantResponse> response = ApiResponse.<MerchantResponse>builder()
                .data(merchantService.changeStatus(id, status))
                .message("Change merchant status successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/merchant/register")
    @Operation(summary = "Register to launch a merchant",
            description = "API for Merchant Admin to register to launch a merchant")
    public ResponseEntity<?> registerMerchant(@RequestBody @Valid MerchantRegistrationRequest request) {
        merchantService.registerMerchant(request);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Your request is pending to be confirmed")
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/merchant/my-merchant")
    @Operation(summary = "Update merchant information",
            description = "API for Merchant Admin to update merchant information")
    public ResponseEntity<?> updateMerchant(@RequestBody @Valid MerchantUpdateRequest request) {
        ApiResponse<MerchantResponse> response = ApiResponse.<MerchantResponse>builder()
                .data(merchantService.updateMerchant(request))
                .message("Update merchant successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
