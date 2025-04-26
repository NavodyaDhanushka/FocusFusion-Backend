package com.example.Backend.dto;

import java.util.List;
import lombok.Data;

@Data
public class EventResponseDTO {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String date;
    private String location;
    private String category;
    private int maxParticipants;
    private List<String> participants;
    private long createdAt;
    private long updatedAt;
}