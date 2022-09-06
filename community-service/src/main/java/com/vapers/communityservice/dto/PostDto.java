package com.vapers.communityservice.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class PostDto {
    private Long id;

    private Long userId;

    private String userToken;

    private String userNickName;

    private String title;

    private String contents;

    private Integer category;
}
