package com.vapers.orderservice.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;
    private Integer status;
    private Integer code;

    public ErrorResponse(String message, ErrorCode code) {
        this.message = message != null ? message : code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }
}
