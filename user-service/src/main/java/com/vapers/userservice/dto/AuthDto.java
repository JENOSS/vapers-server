package com.vapers.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


public class AuthDto {

    @Data
    @AllArgsConstructor
    public static class Token {
        private String userName;
        private String refreshToken;
    }

    @Data
    public static class requestCreate{
        private String userName;
        private String refreshToken;
    }

    @Data
    @AllArgsConstructor
    public static class responseCreate{
        private String accessToken;
        private String refreshToken;
    }

}
