package com.ktpm.potatoapi.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class ApiResponse<T> {
    private T data;
    private String message;
    private Integer status;
}
