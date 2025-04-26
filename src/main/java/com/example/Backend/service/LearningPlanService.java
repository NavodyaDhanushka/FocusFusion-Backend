package com.example.Backend.service;

import com.example.Backend.model.Comment;
import com.example.Backend.model.LearningPlan;
import com.example.Backend.model.Like;
import com.example.Backend.repository.LearningPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LearningPlanService {

    @Autowired
    private LearningPlanRepository learningPlanRepository;

    // Create a new learning plan
    public LearningPlan createLearningPlan(LearningPlan plan) {
        if (plan.getUserId() == null || plan.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (plan.getUserName() == null || plan.getUserName().isEmpty()) {
            plan.setUserName("Unknown User");
        }
        plan.setCreatedAt(new Date());
        plan.setUpdatedAt(new Date());
        plan.setLikes(new ArrayList<>());
        plan.setComments(new ArrayList<>());
        return learningPlanRepository.save(plan);
    }

    // Get all learning plans
    public List<LearningPlan> getAllLearningPlans() {
        return learningPlanRepository.findAllByOrderByCreatedAtDesc();
    }

    // Get a learning plan by ID
    public LearningPlan getLearningPlanById(String id) {
        return learningPlanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Learning plan not found"));
    }

    // Get plans by user ID
    public List<LearningPlan> getLearningPlansByUserId(String userId) {
        return learningPlanRepository.findByUserId(userId);
    }

    // Update learning plan
    public LearningPlan updateLearningPlan(String id, LearningPlan planDetails) {
        LearningPlan plan = getLearningPlanById(id);
        plan.setTitle(planDetails.getTitle());
        plan.setDescription(planDetails.getDescription());
        plan.setTopics(planDetails.getTopics());
        plan.setResources(planDetails.getResources());
        plan.setUpdatedAt(new Date());
        return learningPlanRepository.save(plan);
    }

    // Delete learning plan
    public void deleteLearningPlan(String id) {
        LearningPlan plan = getLearningPlanById(id);
        learningPlanRepository.delete(plan);
    }

    // Add comment
    public LearningPlan addComment(String planId, Comment comment) {
        LearningPlan plan = getLearningPlanById(planId);
        if (plan.getComments() == null) {
            plan.setComments(new ArrayList<>());
        }
        if (comment.getUserName() == null || comment.getUserName().isEmpty()) {
            comment.setUserName("Unknown User");
        }
        comment.setId(UUID.randomUUID().toString());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());
        plan.getComments().add(comment);
        return learningPlanRepository.save(plan);
    }

    // Update comment
    public LearningPlan updateComment(String planId, String commentId, Comment commentDetails) {
        LearningPlan plan = getLearningPlanById(planId);
        plan.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .ifPresent(c -> {
                    c.setContent(commentDetails.getContent());
                    c.setUpdatedAt(new Date());
                });
        return learningPlanRepository.save(plan);
    }

    // Delete comment
    public LearningPlan deleteComment(String planId, String commentId, String userId) {
        LearningPlan plan = getLearningPlanById(planId);
        boolean isOwner = plan.getUserId().equals(userId);
        plan.setComments(plan.getComments().stream()
                .filter(c -> !(c.getId().equals(commentId) && (c.getUserId().equals(userId) || isOwner)))
                .collect(Collectors.toList()));
        return learningPlanRepository.save(plan);
    }

    // Add like
    public LearningPlan addLike(String planId, Like like) {
        LearningPlan plan = getLearningPlanById(planId);
        if (plan.getLikes() == null) {
            plan.setLikes(new ArrayList<>());
        }
        boolean alreadyLiked = plan.getLikes().stream()
                .anyMatch(l -> l.getUserId().equals(like.getUserId()));

        if (!alreadyLiked) {
            like.setCreatedAt(new Date());
            plan.getLikes().add(like);
            return learningPlanRepository.save(plan);
        }
        return plan;
    }

    // Remove like
    public LearningPlan removeLike(String planId, String userId) {
        LearningPlan plan = getLearningPlanById(planId);
        plan.setLikes(plan.getLikes().stream()
                .filter(like -> !like.getUserId().equals(userId))
                .collect(Collectors.toList()));
        return learningPlanRepository.save(plan);
    }
}
