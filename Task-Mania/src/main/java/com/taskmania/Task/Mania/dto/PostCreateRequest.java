package com.taskmania.Task.Mania.dto;

import com.taskmania.Task.Mania.model.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostCreateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String skillCategory;

    private Post.MediaType mediaType;
}


