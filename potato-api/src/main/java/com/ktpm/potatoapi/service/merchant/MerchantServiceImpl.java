package com.ktpm.potatoapi.service.merchant;

import com.ktpm.potatoapi.dto.request.MerchantRegistrationRequest;
import com.ktpm.potatoapi.dto.request.MerchantUpdateRequest;
import com.ktpm.potatoapi.dto.response.MerchantRegistrationResponse;
import com.ktpm.potatoapi.dto.response.MerchantResponse;
import com.ktpm.potatoapi.entity.Merchant;
import com.ktpm.potatoapi.entity.RegisteredMerchant;
import com.ktpm.potatoapi.entity.User;
import com.ktpm.potatoapi.enums.EntityStatus;
import com.ktpm.potatoapi.enums.RegistrationStatus;
import com.ktpm.potatoapi.enums.Role;
import com.ktpm.potatoapi.exception.AppException;
import com.ktpm.potatoapi.exception.ErrorCode;
import com.ktpm.potatoapi.mapper.MerchantMapper;
import com.ktpm.potatoapi.repository.MerchantRepository;
import com.ktpm.potatoapi.repository.RegisteredMerchantRepository;
import com.ktpm.potatoapi.repository.UserRepository;
import com.ktpm.potatoapi.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MerchantServiceImpl implements MerchantService {
    MerchantRepository merchantRepository;
    MerchantMapper mapper;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RegisteredMerchantRepository registeredMerchantRepository;

    @Override
    public List<MerchantResponse> getAllMerchant() {
        return merchantRepository.findAll().stream()
                .map(mapper::toMerchantResponse)
                .toList();
    }

    @Override
    public List<MerchantRegistrationResponse> getAllRegisteredMerchant() {
        return registeredMerchantRepository.findAll().stream()
                .map(mapper::toMerchantRegistrationResponse)
                .toList();
    }

    @Override
    public MerchantResponse getById(Long id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MERCHANT_NOT_FOUND));
        return mapper.toMerchantResponse(merchant);
    }

    @Override
    public MerchantResponse updateMerchant(MerchantUpdateRequest request) {
        String email = SecurityUtils.getCurrentUserEmail();
        Merchant merchant = merchantRepository.findByMerchantAdmin_Email(email)
                .orElseThrow(() -> new AppException(ErrorCode.MERCHANT_NOT_FOUND));

        mapper.update(merchant, request);

        return mapper.toMerchantResponse(merchantRepository.save(merchant));
    }

    @Override
    public MerchantResponse changeStatus(Long id, EntityStatus status) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MERCHANT_NOT_FOUND));

        merchant.setStatus(status);

        return mapper.toMerchantResponse(merchantRepository.save(merchant));
    }

    // merchant name phải unique => kiểm tra duplicate
    @Override
    public void registerMerchant(MerchantRegistrationRequest request) {
        RegisteredMerchant registeredMerchant = mapper.toRegisteredMerchant(request);
        registeredMerchant.setRegistrationStatus(RegistrationStatus.PENDING);
        registeredMerchantRepository.save(registeredMerchant);
    }

    @Override
    @Transactional
    public MerchantResponse approveMerchant(Long id) {
        RegisteredMerchant registeredMerchant = registeredMerchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found registered merchant"));

        // tạo account cho merchant admin
        User merchantAdmin = User.builder()
                .email(registeredMerchant.getEmail())
                .password(passwordEncoder.encode("12345678")) // default password
                .fullName(registeredMerchant.getFullName())
                .role(Role.MERCHANT_ADMIN)
                .build();
        userRepository.save(merchantAdmin);

        // cập nhật trạng thái đăng kí kinh doanh
        registeredMerchant.setRegistrationStatus(RegistrationStatus.APPROVE);
        registeredMerchantRepository.save(registeredMerchant);

        // tạo merchant và gán merchant admin đã tạo
        Merchant merchant = Merchant.builder()
                .name(registeredMerchant.getMerchantName())
                .address(registeredMerchant.getAddress())
                .cuisineTypes(new HashSet<>(registeredMerchant.getCuisineTypes()))
                .merchantAdmin(merchantAdmin)
                .build();

        return mapper.toMerchantResponse(merchantRepository.save(merchant));
    }
}
