package com.vapers.gatewayservice.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
