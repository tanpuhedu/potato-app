package com.ktpm.potatoapi.service.user;

import com.ktpm.potatoapi.payload.request.UserRequest;
import com.ktpm.potatoapi.payload.request.UserSignUpRequest;
import com.ktpm.potatoapi.payload.response.UserLogInResponse;
import com.ktpm.potatoapi.payload.response.UserResponse;
import com.ktpm.potatoapi.entity.User;
import com.ktpm.potatoapi.enums.EntityStatus;
import com.ktpm.potatoapi.enums.Role;
import com.ktpm.potatoapi.mapper.UserMapper;
import com.ktpm.potatoapi.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper mapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserLogInResponse signUp(UserSignUpRequest userSignUpRequest , HttpServletRequest httpRequest) {

        return null;
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
