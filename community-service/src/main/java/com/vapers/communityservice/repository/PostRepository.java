package com.vapers.communityservice.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
    Iterable<PostEntity> findByCategory(Integer category);
    Iterable<PostEntity> findByUserId(Long userId);
    Iterable<PostEntity> findByUserToken(String userToken);
    Iterable<PostEntity> findAll(Sort sort);
}
