package com.ktpm.potatoapi.service.merchant;

import com.ktpm.potatoapi.dto.request.MerchantRequest;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.dto.response.PageResponse;
import com.ktpm.potatoapi.entity.Merchant;
import com.ktpm.potatoapi.mapper.MerchantMapper;
import com.ktpm.potatoapi.repository.MerchantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public PageResponse<MerchantResponse> getByCriteria(
            String keyword, BigDecimal minAvgRating, String cuisineType,
            String sortBy, int pageIdx, int limit
    ) {
        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageIdx, limit, sort);

        Page<Merchant> merchants = merchantRepository.getByCriteria(keyword, minAvgRating, cuisineType, pageable);
        Page<MerchantResponse> merchantResponses = merchants.map(mapper::toResponse);
        return PageResponse.from(merchantResponses);
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
