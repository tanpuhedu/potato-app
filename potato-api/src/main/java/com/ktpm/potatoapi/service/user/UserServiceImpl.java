package com.ktpm.potatoapi.service.user;

import com.ktpm.potatoapi.entity.User;
import com.ktpm.potatoapi.enums.Role;
import com.ktpm.potatoapi.exception.AppException;
import com.ktpm.potatoapi.exception.ErrorCode;
import com.ktpm.potatoapi.mapper.UserMapper;
import com.ktpm.potatoapi.dto.request.UserLogInRequest;
import com.ktpm.potatoapi.dto.request.UserSignUpRequest;
import com.ktpm.potatoapi.dto.response.UserLogInResponse;
import com.ktpm.potatoapi.repository.UserRepository;
import com.ktpm.potatoapi.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    @Override
    public UserLogInResponse signUp(UserSignUpRequest userSignUpRequest , HttpServletRequest httpRequest) {
        if (userRepository.existsByEmail(userSignUpRequest.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        // Create new user
        User user = userMapper.mapSignUpRequestToEntity(userSignUpRequest);
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
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

        // Generate token
        UserLogInResponse userLogInResponse = userMapper.toLogInResponse(user);
        userLogInResponse.setToken(JwtUtils.createToken(user, httpRequest));
        log.info("{} log in success", userLogInResponse.getFullName());

        return userLogInResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> checker = userRepository.findByEmail(username);
        if (checker.isEmpty())
            throw new UsernameNotFoundException(username);

        User user = checker.get();
        Role role = user.getRole();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.name());
        grantedAuthorities.add(simpleGrantedAuthority);

        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
