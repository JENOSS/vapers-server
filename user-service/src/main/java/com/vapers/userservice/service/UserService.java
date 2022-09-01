package com.vapers.userservice.service;


import com.vapers.userservice.dto.UserDto;
import com.vapers.userservice.repository.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserToken(String userToken);
    Iterable<UserEntity> getUserByAll(); // db에 있는걸 있는 그대로 가져올거면 entity 로 가공할거면 dto 로

    UserDto getUserByEmail(String userName);
}
