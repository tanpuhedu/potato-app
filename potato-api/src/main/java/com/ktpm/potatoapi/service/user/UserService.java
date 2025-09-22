package com.ktpm.potatoapi.service.user;

import com.ktpm.potatoapi.dto.request.UserRequest;
import com.ktpm.potatoapi.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();
    UserResponse getById(Long id);
    UserResponse createCustomer(UserRequest request);
    UserResponse update(Long id, UserRequest request);
    UserResponse updateStatus(Long id, int status);
}
