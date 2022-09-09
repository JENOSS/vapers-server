package com.vapers.gatewayservice.exception;

import lombok.Getter;

@Getter
public enum ErrorCode{
    FRAME_WORK_INTERNAL_ERROR(500, 1,"게이트웨이 내부 예외"),
    UNDEFINED_ERROR(500,1,"정의하지 않은 예외"),
    CONNECTION_REFUSED(500,1,"해당 서비스에 접근할 수 없습니다."),
    UNAUTHORIZED_ERROR(401, -1, "인증에 실패했습니다."),
    SERVICE_UNABLE(503,1, "해당 서비스를 찾을 수 없습니다.");

    private final int code;
    private final String message;
    private int status;

    ErrorCode(final int status, final int code, final String message){
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
