package com.example.Backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend.model.Resource;

@Repository
public interface ResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findByUserId(String userId);
    List<Resource> findByTitleContainingIgnoreCase(String title);
}