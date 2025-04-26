package com.example.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.EventDTO;
import com.example.Backend.dto.EventResponseDTO;
import com.example.Backend.service.EventService;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    // Create a new event for a user
    @PostMapping("/user/{userId}")
    public ResponseEntity<EventResponseDTO> createEvent(
            @PathVariable String userId,
            @RequestBody EventDTO dto) {
        EventResponseDTO created = eventService.createEvent(userId, dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAll() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    // Get events by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventResponseDTO>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(eventService.getEventsByUser(userId));
    }

    // Search/filter events
    @GetMapping("/search")
    public ResponseEntity<List<EventResponseDTO>> search(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String location) {
        return ResponseEntity.ok(eventService.searchEvents(category, date, location));
    }

    // Update an event (only owner)
    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable String id,
            @PathVariable String userId,
            @RequestBody EventDTO dto) {
        EventResponseDTO updated = eventService.updateEvent(id, userId, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete an event (only owner)
    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable String id,
            @PathVariable String userId) {
        eventService.deleteEvent(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Register for an event
    @PostMapping("/{id}/register/user/{userId}")
    public ResponseEntity<EventResponseDTO> register(
            @PathVariable String id,
            @PathVariable String userId) {
        EventResponseDTO registered = eventService.registerForEvent(id, userId);
        return new ResponseEntity<>(registered, HttpStatus.OK);
    }

    // Get upcoming events for a user
    @GetMapping("/user/{userId}/upcoming")
    public ResponseEntity<List<EventResponseDTO>> upcoming(
            @PathVariable String userId) {
        return ResponseEntity.ok(eventService.getUpcomingEventsForUser(userId));
    }
}
