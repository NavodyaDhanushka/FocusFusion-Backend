package com.example.Backend.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


public class ResourceController
{
    @Autowired
    private ResourceService resourceService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ResourceResponseDTO> createResource(
            @PathVariable String userId,
            @RequestBody ResourceDTO dto) {

        // Create a logger instance for this class
        Logger logger = LoggerFactory.getLogger(getClass());

        // Log the userId and incoming data
        logger.info("Received request to create resource for user: {}", userId);
        logger.info("Request body: {}", dto);

        // Call the service to create the resource
        ResourceResponseDTO created = resourceService.createResource(userId, dto);

        // Log the result after resource creation
        logger.info("Resource created successfully: {}", created);

        // Return the created resource with status
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResourceResponseDTO>> getAll() {

        return ResponseEntity.ok(resourceService.getAllResources());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResourceResponseDTO>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(resourceService.getResourcesByUser(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResourceResponseDTO>> searchByTitle(
            @RequestParam String title) {

        // Create a logger instance for this class
        Logger logger = LoggerFactory.getLogger(getClass());

        // Log the title received in the request
        logger.info("Received search request with title: {}", title);

        // Fetch the search results
        List<ResourceResponseDTO> searchResults = resourceService.searchByTitle(title);

        // Log the number of results found
        logger.info("Search completed. Found {} results for title: {}", searchResults.size(), title);

        // Return the search results
        return ResponseEntity.ok(searchResults);
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> deleteResource(
            @PathVariable String id,
            @PathVariable String userId) {

        resourceService.deleteResource(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/user/{userId}")
    public ResponseEntity<ResourceResponseDTO> updateResource(
            @PathVariable String id,
            @PathVariable String userId,
            @RequestBody ResourceDTO dto) {

        // Create a logger instance for this class
        Logger logger = LoggerFactory.getLogger(getClass());

        // Log the ID, userId, and incoming request data
        logger.info("Received request to update resource with ID: {} for user: {}", id, userId);
        logger.info("Request body: {}", dto);

        // Call the service to update the resource
        ResourceResponseDTO updated = resourceService.updateResource(id, userId, dto);

        // Log the result after the resource has been updated
        logger.info("Resource updated successfully: {}", updated);

        // Return the updated resource with status
        return ResponseEntity.ok(updated);
    }



}