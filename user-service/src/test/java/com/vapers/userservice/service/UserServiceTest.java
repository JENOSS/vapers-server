package com.vapers.userservice.service;

import com.vapers.userservice.dto.UserDto;
import com.vapers.userservice.repository.UserEntity;
import com.vapers.userservice.repository.UserRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Spy
    ModelMapper mapper;
    @InjectMocks
    UserServiceImpl userService;

    @AfterEach
    void resetMock(){
        reset(userRepository);
        reset(passwordEncoder);
    }

    @Test
    void createUserTest(){
        Mockito.when(userRepository.findByUserName(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any(UserEntity.class))).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encrypted");

        UserDto.requestCreate userDto = new UserDto.requestCreate("userName","realName", "pwd");
        userService.createUser(userDto);
    }

    @Test
    void createUserTest_Duplicate(){
        Mockito.when(userRepository.findByUserName(any(String.class))).thenReturn(Optional.of(new UserEntity()));
        Mockito.when(userRepository.save(any(UserEntity.class))).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encrypted");

        UserDto.requestCreate userDto = new UserDto.requestCreate("userName","realName", "pwd");
        Assertions.assertThrows(DuplicateKeyException.class,
                ()-> userService.createUser(userDto));
    }


}
