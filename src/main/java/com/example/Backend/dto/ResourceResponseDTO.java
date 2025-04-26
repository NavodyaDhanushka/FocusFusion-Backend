package com.example.Backend.dto;

import lombok.Data;

@Data
public class ResourceResponseDTO {

    private String id;
    private String userId;
    private String title;
    private String description;
    private String url;
    private long createdAt;
    private long updatedAt;
}
