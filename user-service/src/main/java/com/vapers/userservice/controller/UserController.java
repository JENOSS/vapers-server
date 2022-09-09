
package com.vapers.userservice.controller;

import com.vapers.userservice.dto.UserDto;
import com.vapers.userservice.repository.UserEntity;
import com.vapers.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    private final Environment env;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(Environment env,
                          UserService userService,
                          ModelMapper mapper) {
        this.env = env;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto.responseCreate> createUser(@RequestBody UserDto.requestCreate user){
        UserDto.responseCreate responseUser = userService.createUser(user);
        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto.info>> getAllUsers(){
        List<UserDto.info> userList = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto.info> getUserByUserId(@PathVariable("id") Long id){
        UserDto.info user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto.info> getUserByUserName(@RequestParam("userName") String userName){
        UserDto.info user = userService.getUserByUserName(userName);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
