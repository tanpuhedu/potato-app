package com.ktpm.potatoapi.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogicCustomException extends RuntimeException {
    String message;
    Integer code;
}
