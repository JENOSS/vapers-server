package com.vapers.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


public class AuthDto {

    @Getter
    public static class Token {
        private String userName;
        private String refreshToken;
    }

    @Getter
    public static class requestCreate{
        private String userName;
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    public static class responseCreate{
        private String accessToken;
        private String refreshToken;
    }

}
