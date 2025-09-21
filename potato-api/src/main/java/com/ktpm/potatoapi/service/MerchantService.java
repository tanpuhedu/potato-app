package com.ktpm.potatoapi.service;

import com.ktpm.potatoapi.dto.request.MerchantRequest;
import com.ktpm.potatoapi.dto.response.MerchantResponse;

import java.util.List;

public interface MerchantService {
    List<MerchantResponse> getAll();
    MerchantResponse create(MerchantRequest request);
    MerchantResponse update(Long id, MerchantRequest request);
    void updateStatus(Long id, int status);
}
