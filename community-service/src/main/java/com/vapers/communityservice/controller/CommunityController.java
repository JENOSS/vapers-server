package com.vapers.communityservice.controller;

import com.vapers.communityservice.dto.PostDto;
import com.vapers.communityservice.repository.PostEntity;
import com.vapers.communityservice.service.CommunityService;
import com.vapers.communityservice.vo.RequestCreatePost;
import com.vapers.communityservice.vo.ResponsePost;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class CommunityController {
    private CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping("/post")
    public ResponseEntity<ResponsePost> createPost(@RequestBody RequestCreatePost requestCreatePost){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        PostDto postDto = mapper.map(requestCreatePost, PostDto.class);
        PostEntity postEntity = communityService.createPost(postDto);
        ResponsePost responsePost = mapper.map(postEntity, ResponsePost.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responsePost);
    }

    @GetMapping("/posts/{userToken}")
    public ResponseEntity<List<ResponsePost>> getPostsByUserToken(@PathVariable("userToken") String userToken){
        Iterable<PostEntity> postEntities = communityService.getPostsByUserToken(userToken);

        List<ResponsePost> result = new ArrayList<>();
        postEntities.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponsePost.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/posts/hot")
    public ResponseEntity<List<ResponsePost>> getPostsByViewsDesc(){
        Iterable<PostEntity> postEntities = communityService.getPostsSortedViews();

        List<ResponsePost> result = new ArrayList<>();
        postEntities.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponsePost.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
