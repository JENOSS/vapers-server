package com.vapers.userservice.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
    private ErrorCode code;

    public UnauthorizedException(){
        super(ErrorCode.UNAUTHORIZED_ERROR.getMessage());
        this.code = ErrorCode.UNAUTHORIZED_ERROR;
    }
}
