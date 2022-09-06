package com.vapers.userservice.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserToken(String userToken);
    UserEntity findByEmail(String userEmail);

    UserEntity findByNickName(String nickName);
}
