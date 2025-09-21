package com.ktpm.potatoapi.controller;

import com.ktpm.potatoapi.dto.request.MerchantRequest;
import com.ktpm.potatoapi.dto.response.ApiResponse;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.dto.response.PageResponse;
import com.ktpm.potatoapi.service.merchant.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Merchant Controller")
public class MerchantController {
    MerchantService merchantService;

    @GetMapping("/admin/merchants")
    @Operation(summary = "get list of all merchants")
    public ApiResponse<List<MerchantResponse>> getAll() {
        return ApiResponse.<List<MerchantResponse>>builder()
                .message("get list of all merchants successfully")
                .data(merchantService.getAll())
                .build();
    }

    @GetMapping("/merchants")
    @Operation(summary = "get list of merchants by criteria")
    public ApiResponse<PageResponse<MerchantResponse>> getByCriteria(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minAvgRating,
            @RequestParam(required = false) String cuisineType,
            @RequestParam(defaultValue = "avgRating") String sortBy,
            @RequestParam(defaultValue = "0") int pageIdx,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ApiResponse.<PageResponse<MerchantResponse>>builder()
                .message("get list of merchants by criteria successfully")
                .data(merchantService.getByCriteria(keyword, minAvgRating, cuisineType, sortBy, pageIdx, limit))
                .build();
    }

    @PostMapping("/admin/merchants")
    @Operation(summary = "create new merchant")
    public ApiResponse<MerchantResponse> create(@RequestBody MerchantRequest request) {
        return ApiResponse.<MerchantResponse>builder()
                .message("create new merchant successfully")
                .data(merchantService.create(request))
                .build();
    }

    @PutMapping("/merchant/store/{id}")
    @Operation(summary = "update merchant information")
    public ApiResponse<MerchantResponse> update(@PathVariable Long id,
                                                   @RequestBody MerchantRequest request) {
        return ApiResponse.<MerchantResponse>builder()
                .message("update merchant information successfully")
                .data(merchantService.update(id, request))
                .build();
    }

    @PutMapping("/admin/merchants/{id}")
    @Operation(summary = "update merchant status")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @RequestParam int status) {
        merchantService.updateStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("update merchant status successfully")
                .build();
    }
}
