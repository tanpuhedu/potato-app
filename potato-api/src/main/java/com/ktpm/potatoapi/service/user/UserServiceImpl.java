package com.ktpm.potatoapi.service.user;

import com.ktpm.potatoapi.entity.Merchant;
import com.ktpm.potatoapi.entity.User;
import com.ktpm.potatoapi.enums.EntityStatus;
import com.ktpm.potatoapi.enums.Role;
import com.ktpm.potatoapi.exception.AppException;
import com.ktpm.potatoapi.exception.ErrorCode;
import com.ktpm.potatoapi.mapper.UserMapper;
import com.ktpm.potatoapi.dto.request.UserLogInRequest;
import com.ktpm.potatoapi.dto.request.UserSignUpRequest;
import com.ktpm.potatoapi.dto.response.UserLogInResponse;
import com.ktpm.potatoapi.repository.MerchantRepository;
import com.ktpm.potatoapi.repository.UserRepository;
import com.ktpm.potatoapi.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    MerchantRepository merchantRepository;

    @Override
    public UserLogInResponse signUp(UserSignUpRequest userSignUpRequest , HttpServletRequest httpRequest) {
        if (userRepository.existsByEmail(userSignUpRequest.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        // Create new user
        User user = userMapper.mapSignUpRequestToEntity(userSignUpRequest);
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);

        // Generate Jwt token
        UserLogInResponse userLogInResponse = userMapper.toLogInResponse(user);
        userLogInResponse.setToken(JwtUtils.createToken(user, httpRequest));
        log.info("{} sign up success", user.getEmail());

        return userLogInResponse;
    }

    @Override
    public UserLogInResponse logIn(UserLogInRequest userLogInRequest, HttpServletRequest httpRequest) {
        User user = userRepository.findByEmail(userLogInRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean isAuthenticated = passwordEncoder.matches(userLogInRequest.getPassword(),user.getPassword());
        if(!isAuthenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (user.getRole() == Role.MERCHANT_ADMIN) {
            Merchant merchant = merchantRepository.findByMerchantAdmin_Email(userLogInRequest.getEmail())
                    .orElseThrow(() -> new AppException(ErrorCode.MERCHANT_NOT_FOUND));

            if (merchant.getStatus() == EntityStatus.INACTIVE)
                throw new AppException(ErrorCode.MERCHANT_INACTIVE);
        }

        // Generate token
        UserLogInResponse userLogInResponse = userMapper.toLogInResponse(user);
        userLogInResponse.setToken(JwtUtils.createToken(user, httpRequest));
        log.info("{} log in success", userLogInResponse.getFullName());

        return userLogInResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (user.getRole() == Role.MERCHANT_ADMIN) {
            Merchant merchant = merchantRepository.findByMerchantAdmin_Email(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Merchant not found for admin: " + email));

            if (merchant.getStatus() == EntityStatus.INACTIVE) {
                throw new DisabledException("Merchant inactive for user: " + email);
            }
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}
