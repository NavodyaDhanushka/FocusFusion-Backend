package com.example.Backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.dto.ResourceDTO;
import com.example.Backend.dto.ResourceResponseDTO;
import com.example.Backend.model.Resource;
import com.example.Backend.repository.ResourceRepository;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository repository;

    public ResourceResponseDTO createResource(String userId, ResourceDTO dto) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(dto, resource);
        resource.setUserId(userId);
        long now = System.currentTimeMillis();
        resource.setCreatedAt(now);
        resource.setUpdatedAt(now);
        Resource saved = repository.save(resource);
        return toDto(saved);
    }

    public List<ResourceResponseDTO> getAllResources() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ResourceResponseDTO getResourceById(String id) {
        Resource res = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        return toDto(res);
    }

    public List<ResourceResponseDTO> getResourcesByUser(String userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ResourceResponseDTO> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ResourceResponseDTO updateResource(String id, String userId, ResourceDTO dto) {
        Resource existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setUrl(dto.getUrl());
        existing.setUpdatedAt(System.currentTimeMillis());
        Resource updated = repository.save(existing);
        return toDto(updated);
    }

    public void deleteResource(String id, String userId) {
        Resource existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        repository.delete(existing);
    }

    private ResourceResponseDTO toDto(Resource res) {
        ResourceResponseDTO dto = new ResourceResponseDTO();
        BeanUtils.copyProperties(res, dto);
        return dto;
    }
}
