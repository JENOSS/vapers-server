package com.vapers.communityservice.vo;

import lombok.Data;

@Data
public class ResponseUser {
    private Long id;
    private String email;
    private String name;
    private String nickName;
    private String userToken;
}
