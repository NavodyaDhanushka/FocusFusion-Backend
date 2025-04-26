package com.taskmania.Task.Mania.repositories;

import com.taskmania.Task.Mania.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String taskId);
    long countByPostId(String postId);
}
