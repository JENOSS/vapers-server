package com.vapers.gatewayservice.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{
    private ErrorCode code;

    public UnauthorizedException(ErrorCode code){
        super(code.getMessage());
        this.code = code;
    }

}
