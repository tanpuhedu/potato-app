package com.ktpm.potatoapi.mapper;

import com.ktpm.potatoapi.dto.request.MerchantRegistrationRequest;
import com.ktpm.potatoapi.dto.request.MerchantUpdateRequest;
import com.ktpm.potatoapi.dto.response.MerchantRegistrationResponse;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.entity.Merchant;
import com.ktpm.potatoapi.entity.RegisteredMerchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MerchantMapper {
    RegisteredMerchant toRegisteredMerchant(MerchantRegistrationRequest dto);
    MerchantResponse toMerchantResponse(Merchant entity);
    MerchantRegistrationResponse toMerchantRegistrationResponse(RegisteredMerchant entity);
    void update(@MappingTarget Merchant entity, MerchantUpdateRequest dto);
}
