package com.vapers.communityservice.service;

import com.vapers.communityservice.client.UserServiceClient;
import com.vapers.communityservice.dto.PostDto;
import com.vapers.communityservice.repository.PostEntity;
import com.vapers.communityservice.repository.PostRepository;
import com.vapers.communityservice.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommunityServiceImpl implements CommunityService{
    private final PostRepository postRepository;
    private UserServiceClient userServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    @Autowired
    public CommunityServiceImpl(PostRepository postRepository,
                                UserServiceClient userServiceClient,
                                CircuitBreakerFactory circuitBreakerFactory) {
        this.postRepository = postRepository;
        this.userServiceClient = userServiceClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }


    @Override
    public PostEntity createPost(PostDto postDto) {

        log.info("before call user-service");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        ResponseUser user = circuitBreaker.run(() -> userServiceClient.getUser(postDto.getUserToken()),
                throwable -> null);
        log.info("after call user-service");

        postDto.setUserId(user != null ? user.getId() :  -1);
        postDto.setUserNickName(user != null ? user.getNickName() : "");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PostEntity postEntity = mapper.map(postDto, PostEntity.class);

        return postRepository.save(postEntity);
    }

    @Override
    public Iterable<PostEntity> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Iterable<PostEntity> getPostsSortedViews() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "views"));
    }

    @Override
    public Iterable<PostEntity> getPostsByCategory(Integer category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public Iterable<PostEntity> getPostsByUserToken(String userToken) {
        return postRepository.findByUserToken(userToken);
    }
}
