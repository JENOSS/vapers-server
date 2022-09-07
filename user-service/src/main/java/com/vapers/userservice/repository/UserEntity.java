package com.vapers.userservice.repository;

import lombok.Data;
import lombok.Getter;
import org.springframework.integration.dsl.IntegrationDsl;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String userName;

    @Column(nullable = false, length = 50)
    private String realName;

    @Column(nullable = false, unique = true)
    private String encryptedPwd;

    public void setEncryptedPwd(String encryptedPwd){
        this.encryptedPwd = encryptedPwd;
    }
}
