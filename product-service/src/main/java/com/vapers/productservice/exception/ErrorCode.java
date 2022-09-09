package com.vapers.productservice.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, 1,"서버 내부 예외"),
    UNDEFINED_ERROR(500,1,"정의하지 않은 예외"),
    NO_SEARCH_ELEMENT_ERROR(404, 1, "해당 정보를 찾을 수 없습니다.");

    private final int code;
    private final String message;
    private int status;

    ErrorCode(final int status, final int code, final String message){
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
