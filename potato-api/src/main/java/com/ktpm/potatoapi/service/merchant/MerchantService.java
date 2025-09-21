package com.ktpm.potatoapi.service.merchant;

import com.ktpm.potatoapi.dto.request.MerchantRequest;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.dto.response.PageResponse;

import java.math.BigDecimal;
import java.util.List;

public interface MerchantService {
    List<MerchantResponse> getAll();
    PageResponse<MerchantResponse> getByCriteria(
            String keyword,
            BigDecimal minAvgRating,
            String cuisineType,
            String sortBy,
            int pageIdx,
            int limit
    );
    MerchantResponse create(MerchantRequest request);
    MerchantResponse update(Long id, MerchantRequest request);
    void updateStatus(Long id, int status);
}
