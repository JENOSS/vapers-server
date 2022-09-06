package com.vapers.communityservice.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userNickName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Integer category;

    @Column(nullable = false)
    private Integer clicks;
}
