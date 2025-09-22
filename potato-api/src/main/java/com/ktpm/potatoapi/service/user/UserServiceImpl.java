package com.ktpm.potatoapi.service.user;

import com.ktpm.potatoapi.dto.request.UserRequest;
import com.ktpm.potatoapi.dto.response.UserResponse;
import com.ktpm.potatoapi.entity.User;
import com.ktpm.potatoapi.enums.EntityStatus;
import com.ktpm.potatoapi.enums.Role;
import com.ktpm.potatoapi.mapper.UserMapper;
import com.ktpm.potatoapi.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper mapper;
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        return mapper.toResponse(user);
    }

    @Override
    public UserResponse createCustomer(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("existed");

        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER.name());
        return mapper.toResponse(userRepository.save(user));    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        mapper.update(user, request);

        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateStatus(Long id, int status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        // validate status from request param
        if (status != EntityStatus.INACTIVE.getCode() && status != EntityStatus.ACTIVE.getCode())
            throw new RuntimeException("invalid status");

        user.setStatus(status);

        return mapper.toResponse(userRepository.save(user));
    }
}
