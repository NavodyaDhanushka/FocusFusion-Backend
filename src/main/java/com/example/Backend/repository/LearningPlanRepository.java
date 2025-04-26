package com.example.Backend.repository;

import com.example.Backend.model.LearningPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningPlanRepository extends MongoRepository<LearningPlan, String> {

    // Find all learning plans ordered by creation date descending
    List<LearningPlan> findAllByOrderByCreatedAtDesc();

    // Find all learning plans by userId
    List<LearningPlan> findByUserId(String userId);

    List<LearningPlan> findByUserIdOrderByCreatedAtDesc(String userId);
}
