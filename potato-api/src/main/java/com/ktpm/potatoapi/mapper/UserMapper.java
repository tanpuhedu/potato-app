package com.ktpm.potatoapi.mapper;

import com.ktpm.potatoapi.payload.request.UserRequest;
import com.ktpm.potatoapi.payload.response.UserResponse;
import com.ktpm.potatoapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User entity);
    User toEntity(UserRequest dto);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    void update(@MappingTarget User user, UserRequest dto);
}
