package com.vapers.userservice.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "auth")
public class AuthEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String refreshToken;

    public void changeUserName(String userName){
        this.userName = userName;
    }

    public void changeRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
