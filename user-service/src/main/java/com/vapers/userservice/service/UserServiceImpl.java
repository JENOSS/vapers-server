package com.vapers.userservice.service;


import com.vapers.userservice.dto.UserDto;
import com.vapers.userservice.repository.UserEntity;
import com.vapers.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           Environment env,
                           ModelMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUserName(username);

        if (userEntity.isEmpty()) throw new UsernameNotFoundException(username);

        // 마지막 리스트는 권한
        return new User(userEntity.get().getUserName(), userEntity.get().getEncryptedPwd()
                , true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public UserDto.responseCreate createUser(UserDto.requestCreate userDto) {
        validateDuplicateUser(userDto.getUserName());

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.responseCreate.class);
    }

    @Override
    public UserDto.info getUserByUserName(String userName){
        Optional<UserEntity> userEntity = userRepository.findByUserName(userName);

        if(userEntity.isEmpty())
            throw new UsernameNotFoundException(userName);

        return mapper.map(userEntity, UserDto.info.class);
    }

    @Override
    public List<UserDto.info> getAllUsers() {
        List<UserDto.info> result = new ArrayList<>();

        userRepository.findAll().forEach(v->{
            result.add(mapper.map(v, UserDto.info.class));
        });

        return result;
    }

    @Override
    public UserDto.info getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity.isEmpty())
            throw new NoSuchElementException(id.toString());

        return mapper.map(userEntity, UserDto.info.class);
    }

    private void validateDuplicateUser(String userName) {
        if(userRepository.findByUserName(userName).isPresent()){
            throw new DuplicateKeyException(userName);
        }
    }


}
