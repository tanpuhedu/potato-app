package com.ktpm.potatoapi.mapper;

import com.ktpm.potatoapi.dto.request.MerchantRequest;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MerchantMapper {
    MerchantResponse toResponse(Merchant entity);
    Merchant toEntity(MerchantRequest dto);

    @Mapping(target = "name", ignore = true)
    void update(@MappingTarget Merchant entity, MerchantRequest dto);
}
