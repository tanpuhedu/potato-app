package com.ktpm.potatoapi.service.user;

import com.ktpm.potatoapi.exception.LogicCustomException;
import com.ktpm.potatoapi.payload.request.UserLogInRequest;
import com.ktpm.potatoapi.payload.request.UserRequest;
import com.ktpm.potatoapi.payload.request.UserSignUpRequest;
import com.ktpm.potatoapi.payload.response.UserLogInResponse;
import com.ktpm.potatoapi.payload.response.UserResponse;
import com.ktpm.potatoapi.entity.User;
import com.ktpm.potatoapi.enums.EntityStatus;
import com.ktpm.potatoapi.enums.Role;
import com.ktpm.potatoapi.mapper.UserMapper;
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
        Optional<User> checker = userRepository.findByEmail(userSignUpRequest.getEmail());
        if (checker.isPresent()) {
            log.error("Email already exist");
            LogicCustomException logicCustomException = new LogicCustomException();
            logicCustomException.setMessage("Email is already in use");
            logicCustomException.setCode(400);
            throw logicCustomException;
        }

        // Create new user
        User user = userMapper.mapSignUpToEntity(userSignUpRequest);
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        User savedUser = userRepository.save(user);

        //Generate Jwt token
        UserLogInResponse userLogInResponse = new UserLogInResponse();
        userLogInResponse.setFullName(savedUser.getFullName());
        userLogInResponse.setEmail(savedUser.getEmail());
        userLogInResponse.setToken(JwtUtils.createToken(savedUser, httpRequest));
        log.info("{} sign up success", savedUser.getEmail());

        return userLogInResponse;
    }

    @Override
    public UserLogInResponse logIn(UserLogInRequest userLogInRequest, HttpServletRequest request) {
        Optional<User> checker = userRepository.findByEmail(userLogInRequest.getEmail());
        if(checker.isEmpty()) {
            log.error("Email or password is incorrect");
            LogicCustomException logicCustomException = new LogicCustomException();
            logicCustomException.setMessage("Username or password is incorrect");
            logicCustomException.setCode(401);
            throw logicCustomException;
        }
        if(!passwordEncoder.matches(userLogInRequest.getPassword(), checker.get().getPassword())) {
            log.error("Email or password is incorrect");
            LogicCustomException logicCustomException = new LogicCustomException();
            logicCustomException.setMessage("Username or password is incorrect");
            logicCustomException.setCode(401);
            throw logicCustomException;
        }

        // Generate token
        UserLogInResponse userLogInResponse = new UserLogInResponse();
        userLogInResponse.setFullName(checker.get().getFullName());
        userLogInResponse.setEmail(checker.get().getEmail());
        userLogInResponse.setToken(JwtUtils.createToken(checker.get(), request));
        log.info("{} log in success", userLogInResponse.getFullName());

        return userLogInResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> checker = userRepository.findByEmail(username);
        if (checker.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User user = checker.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Role role = user.getRole();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.name());
        grantedAuthorities.add(simpleGrantedAuthority);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    // Phú thành code
    /*
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

     */
}
