package com.example.Backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "resources")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Resource {
    @Id
    private String id;
    private String userId;
    private String title;
    private String description;
    private String url;
    private long createdAt;
    private long updatedAt;
}