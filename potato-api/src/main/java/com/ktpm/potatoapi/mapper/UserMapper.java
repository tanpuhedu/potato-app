package com.ktpm.potatoapi.mapper;

import com.ktpm.potatoapi.payload.request.UserRequest;
import com.ktpm.potatoapi.payload.request.UserSignUpRequest;
import com.ktpm.potatoapi.payload.response.UserResponse;
import com.ktpm.potatoapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // For sign up
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    User mapSignUpToEntity(UserSignUpRequest userSignUpRequest);

    // Thành code
    /*
    @Mapping(target = "status", ignore = true)
    UserResponse toResponse(User entity);

    @Mapping(target = "status", ignore = true)
    User toEntity(UserRequest dto);

    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    void update(@MappingTarget User user, UserRequest dto);

     */
}
