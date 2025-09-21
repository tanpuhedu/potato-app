package com.ktpm.potatoapi.service;

import com.ktpm.potatoapi.dto.request.MerchantRequest;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.entity.Merchant;
import com.ktpm.potatoapi.mapper.MerchantMapper;
import com.ktpm.potatoapi.repository.MerchantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantServiceImpl implements MerchantService {
    MerchantRepository merchantRepository;
    MerchantMapper mapper;

    @Override
    public List<MerchantResponse> getAll() {
        return merchantRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public MerchantResponse create(MerchantRequest request) {
        if (merchantRepository.existsByName(request.getName()))
            throw new RuntimeException("existed");

        Merchant merchant = mapper.toEntity(request);

        return mapper.toResponse(merchantRepository.save(merchant));
    }

    @Override
    public MerchantResponse update(Long id, MerchantRequest request) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        mapper.update(merchant, request);

        return mapper.toResponse(merchantRepository.save(merchant));
    }

    @Override
    public void updateStatus(Long id, int status) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        merchant.setStatus(status);

        merchantRepository.save(merchant);
    }
}
