package com.taskmania.Task.Mania.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    private String id;

    @Indexed
    private String userId;

    private String title;
    private String description;

    private List<String> mediaUrls = new ArrayList<>();
    private MediaType mediaType;

    private List<String> likedBy = new ArrayList<>();
    private String skillCategory;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public enum MediaType {
        PHOTO,VIDEO
    }
}
