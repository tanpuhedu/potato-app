package com.ktpm.potatoapi.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    T data;
    String message;

    @Builder.Default
    Integer status = 1000; // Default status successful
}
