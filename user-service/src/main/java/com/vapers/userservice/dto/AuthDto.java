package com.vapers.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


public class AuthDto {

    @Getter
    @AllArgsConstructor
    public static class Token {
        private String userName;
        private String refreshToken;
    }


    @AllArgsConstructor
    public static class responseCreate{
        private String accessToken;
        private String refreshToken;
    }

}
