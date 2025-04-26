package com.taskmania.Task.Mania.services;

import com.taskmania.Task.Mania.dto.PostCreateRequest;
import com.taskmania.Task.Mania.model.Post;
import com.taskmania.Task.Mania.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private Post createPost(PostCreateRequest postCreateRequest, String userId, List<MultiPartFiles>) {}
}
