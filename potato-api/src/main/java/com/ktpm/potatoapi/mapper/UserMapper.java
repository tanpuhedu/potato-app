package com.ktpm.potatoapi.mapper;

import com.ktpm.potatoapi.entity.User;
import com.ktpm.potatoapi.dto.request.UserSignUpRequest;
import com.ktpm.potatoapi.dto.response.UserLogInResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // For sign up
    @Mapping(target = "password", ignore = true)
    User mapSignUpRequestToEntity(UserSignUpRequest userSignUpRequest);

    UserLogInResponse toLogInResponse(User user);
}
