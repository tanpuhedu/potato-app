package com.ktpm.potatoapi.service.merchant;

import com.ktpm.potatoapi.dto.request.MerchantRegistrationRequest;
import com.ktpm.potatoapi.dto.request.MerchantUpdateRequest;
import com.ktpm.potatoapi.dto.response.MerchantRegistrationResponse;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.enums.EntityStatus;

import java.util.List;

public interface MerchantService {
    List<MerchantResponse> getAllMerchant();
    List<MerchantRegistrationResponse> getAllRegisteredMerchant();
    MerchantResponse getById(Long id);
    MerchantResponse updateMerchant(MerchantUpdateRequest request);
    MerchantResponse changeStatus(Long id, EntityStatus status);
    void registerMerchant(MerchantRegistrationRequest request);
    MerchantResponse approveMerchant(Long id);
}
