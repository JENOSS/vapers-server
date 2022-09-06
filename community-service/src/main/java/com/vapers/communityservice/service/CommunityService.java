package com.vapers.communityservice.service;

import com.vapers.communityservice.dto.PostDto;
import com.vapers.communityservice.repository.PostEntity;

public interface CommunityService {
    PostEntity createPost(PostDto postDto);
    Iterable<PostEntity> getPosts();
    Iterable<PostEntity> getPostsSortedViews();
    Iterable<PostEntity> getPostsByCategory(Integer category);
    Iterable<PostEntity> getPostByUserToken(String userToken);

}
