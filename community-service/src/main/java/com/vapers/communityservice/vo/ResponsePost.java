package com.vapers.communityservice.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ResponsePost {
    private Long id;

    private Long userId;

    private String userNickName;

    private String title;

    private String contents;

    private Integer category;

    private Integer clicks;
}
