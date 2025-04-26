package com.taskmania.Task.Mania.dto;

import com.taskmania.Task.Mania.model.Post;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostResponse {

    private String title;
    private String description;
    private List<String> mediaUrls;
    private String skillCategory;
    private Post.MediaType mediaType;
    private int likesCount;
    private boolean userLiked;
    private UserBasicDto creator;
    private Date createdAt;
}
