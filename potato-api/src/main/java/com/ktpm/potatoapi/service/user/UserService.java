package com.ktpm.potatoapi.service.user;

import com.ktpm.potatoapi.payload.request.UserRequest;
import com.ktpm.potatoapi.payload.request.UserSignUpRequest;
import com.ktpm.potatoapi.payload.response.UserLogInResponse;
import com.ktpm.potatoapi.payload.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {


    /**
     * Registers a new user account (sign up).
     *
     * @param userSignUpRequest the registration information provided by the user
     *                          (email, password, full name)
     * @param httpRequest the current HTTP request (can be used for logging or authorization)
     * @return a {@link UserLogInResponse} containing the username, email, and generated token
     */
    UserLogInResponse signUp(UserSignUpRequest userSignUpRequest, HttpServletRequest httpRequest);

    // Phú thành code
    /*

    List<UserResponse> getAll();
    UserResponse getById(Long id);
    UserResponse createCustomer(UserRequest request);
    UserResponse update(Long id, UserRequest request);
    UserResponse updateStatus(Long id, int status);

     */
}
