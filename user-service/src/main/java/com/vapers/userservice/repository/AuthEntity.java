package com.vapers.userservice.repository;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "auth")
public class AuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String refreshToken;

}
