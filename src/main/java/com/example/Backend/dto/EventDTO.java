package com.example.Backend.dto;

import lombok.Data;

@Data
public class EventDTO {
    private String title;
    private String description;
    private String date; // ISO format: yyyy-MM-dd
    private String location;
    private String category;
    private int maxParticipants;
}
