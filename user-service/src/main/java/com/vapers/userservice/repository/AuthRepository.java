package com.vapers.userservice.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthRepository extends CrudRepository<AuthEntity, Long> {
    Optional<AuthEntity> findByUserName(String userName);
    Optional<AuthEntity> findByRefreshToken(String refreshToken);
    void deleteAllByUserName(String userName);
}
