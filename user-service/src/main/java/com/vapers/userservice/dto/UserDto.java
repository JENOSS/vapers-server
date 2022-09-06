package com.vapers.userservice.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String name;
    private String nickName;
    private String pwd;
    private String userToken;
    private Date createdAt;
    private String encryptedPwd;
}
