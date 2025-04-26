package com.example.Backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend.model.Event;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByUserId(String userId);
}