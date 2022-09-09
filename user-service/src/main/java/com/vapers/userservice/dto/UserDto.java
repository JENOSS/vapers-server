package com.vapers.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

public class UserDto {

    @Getter
    public static class info{
        private Long id;
        private String userName;
        private String realName;
    }
    @Getter
    public static class requestCreate{
        private String userName;
        private String realName;
        private String pwd;
    }

    @Getter
    public static class requestLogin{
        private String userName;
        private String pwd;
    }
    @Getter
    public static class responseCreate{
        private String userName;
        private String realName;
    }
}
