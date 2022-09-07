package com.vapers.userservice.service;


import com.vapers.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto.responseCreate createUser(UserDto.requestCreate userDto);
    List<UserDto.info> getAllUsers();
    UserDto.info getUserByUserName(String userName);

    UserDto.info getUserById(Long id);
}
