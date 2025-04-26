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

import com.example.Backend.dto.ResourceDTO;
import com.example.Backend.dto.ResourceResponseDTO;
import com.example.Backend.service.ResourceService;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "*")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    // Create a new resource for a specific user
    @PostMapping("/user/{userId}")
    public ResponseEntity<ResourceResponseDTO> createResource(
            @PathVariable String userId,
            @RequestBody ResourceDTO dto) {
        ResourceResponseDTO created = resourceService.createResource(userId, dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get all resources
    @GetMapping
    public ResponseEntity<List<ResourceResponseDTO>> getAll() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    // Get resource by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    // Get all resources by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResourceResponseDTO>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(resourceService.getResourcesByUser(userId));
    }

    // Search resources by title
    @GetMapping("/search")
    public ResponseEntity<List<ResourceResponseDTO>> searchByTitle(
            @RequestParam String title) {
        return ResponseEntity.ok(resourceService.searchByTitle(title));
    }

    // Update a resource (only owner)
    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<ResourceResponseDTO> updateResource(
            @PathVariable String id,
            @PathVariable String userId,
            @RequestBody ResourceDTO dto) {
        ResourceResponseDTO updated = resourceService.updateResource(id, userId, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete a resource (only owner)
    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> deleteResource(
            @PathVariable String id,
            @PathVariable String userId) {
        resourceService.deleteResource(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}