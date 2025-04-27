package com.example.Backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.dto.EventDTO;
import com.example.Backend.dto.EventResponseDTO;
import com.example.Backend.model.Event;
import com.example.Backend.repository.EventRepository;

// Event Service
@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public EventResponseDTO createEvent(String userId, EventDTO dto) {
        Event event = new Event();
        BeanUtils.copyProperties(dto, event);
        event.setUserId(userId);
        long now = System.currentTimeMillis();
        event.setCreatedAt(now);
        event.setUpdatedAt(now);
        Event saved = repository.save(event);
        return toDto(saved);
    }

    public List<EventResponseDTO> getAllEvents() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EventResponseDTO getEventById(String id) {
        Event ev = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return toDto(ev);
    }

    public List<EventResponseDTO> getEventsByUser(String userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<EventResponseDTO> searchEvents(String category, String date, String location) {
        return repository.findAll().stream()
                .filter(ev -> category == null || ev.getCategory().equalsIgnoreCase(category))
                .filter(ev -> date == null || ev.getDate().equals(date))
                .filter(ev -> location == null || ev.getLocation().equalsIgnoreCase(location))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EventResponseDTO updateEvent(String id, String userId, EventDTO dto) {
        Event existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setDate(dto.getDate());
        existing.setLocation(dto.getLocation());
        existing.setCategory(dto.getCategory());
        existing.setMaxParticipants(dto.getMaxParticipants());
        existing.setUpdatedAt(System.currentTimeMillis());
        Event updated = repository.save(existing);
        return toDto(updated);
    }

    public void deleteEvent(String id, String userId) {
        Event existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        repository.delete(existing);
    }

    public EventResponseDTO registerForEvent(String id, String userId) {
        Event ev = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (ev.getUserId().equals(userId)) {
            throw new RuntimeException("Cannot register for your own event");
        }
        if (ev.getParticipants().contains(userId)) {
            throw new RuntimeException("Already registered");
        }
        if (ev.getParticipants().size() >= ev.getMaxParticipants()) {
            throw new RuntimeException("Event full");
        }
        ev.getParticipants().add(userId);
        ev.setUpdatedAt(System.currentTimeMillis());
        Event updated = repository.save(ev);
        return toDto(updated);
    }

    public List<EventResponseDTO> getUpcomingEventsForUser(String userId) {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream()
                .filter(ev -> ev.getParticipants().contains(userId))
                .filter(ev -> LocalDate.parse(ev.getDate()).isAfter(today) || LocalDate.parse(ev.getDate()).isEqual(today))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EventResponseDTO toDto(Event ev) {
        EventResponseDTO dto = new EventResponseDTO();
        BeanUtils.copyProperties(ev, dto);
        return dto;
    }
}