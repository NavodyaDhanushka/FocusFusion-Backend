package com.example.Backend.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//create event model

@Document(collection = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    private String userId;
    private String title;
    private String description;
    private String date; //yyyy-MM-dd
    private String location;
    private String category;
    private int maxParticipants;
    private List<String> participants = new ArrayList<>();
    private long createdAt;
    private long updatedAt;
}