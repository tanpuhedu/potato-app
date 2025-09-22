package com.ktpm.potatoapi.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    T data;
    String message;

    @Builder.Default
    Integer status = 1000; // Default status successful
}