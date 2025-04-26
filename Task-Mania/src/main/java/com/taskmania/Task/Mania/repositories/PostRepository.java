package com.taskmania.Task.Mania.repositories;

import com.taskmania.Task.Mania.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUserId(String userId, Pageable pageable);
    List<Post> findBYUserIdIn(List<String> userIds, Pageable pageable);
    List<Post> findBySkillCategory(String skillCategory, Pageable pageable);
}


