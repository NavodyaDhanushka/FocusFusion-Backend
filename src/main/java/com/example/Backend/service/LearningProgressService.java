package com.example.Backend.service;

import com.example.Backend.model.Comment;
import com.example.Backend.model.LearningProgress;
import com.example.Backend.model.Like;
import com.example.Backend.repository.LearningProgressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for handling all business logic related to Learning Progress entries.
 * Manages CRUD operations, comments, likes, and notifications for learning progress tracking.
 */
@Service // Marks this class as a Spring service component
public class LearningProgressService {

    // Repository for database operations
    private final LearningProgressRepository learningProgressRepository;
    // Service for handling notifications
    private final NotificationService notificationService;

    /**
     * Constructor for dependency injection
     * @param learningProgressRepository Repository for learning progress data access
     * @param notificationService Service for handling notifications
     */
    public LearningProgressService(LearningProgressRepository learningProgressRepository, 
                                  NotificationService notificationService) {
        this.learningProgressRepository = learningProgressRepository;
        this.notificationService = notificationService;
    }

    /**
     * Creates a new learning progress entry with validation
     * @param progress The learning progress data to create
     * @return The created learning progress entry
     * @throws IllegalArgumentException if required fields are missing
     */
    public LearningProgress createLearningProgress(LearningProgress progress) {
        // Validate required fields
        if (progress.getUserId() == null || progress.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (progress.getUserName() == null || progress.getUserName().isEmpty()) {
            progress.setUserName("Unknown User");
        }
        if (progress.getTemplateType() == null || progress.getTemplateType().isEmpty()) {
            throw new IllegalArgumentException("Template type is required");
        }

        // Template-specific validation
        switch (progress.getTemplateType()) {
            case "general":
                if (progress.getTitle() == null || progress.getTitle().isEmpty() ||
                        progress.getDescription() == null || progress.getDescription().isEmpty()) {
                    throw new IllegalArgumentException("Title and description are required for general template");
                }
                break;
            case "tutorial":
                if (progress.getTitle() == null || progress.getTitle().isEmpty() ||
                        progress.getTutorialName() == null || progress.getTutorialName().isEmpty()) {
                    throw new IllegalArgumentException("Title and tutorial name are required for tutorial template");
                }
                break;
            case "project":
                if (progress.getTitle() == null || progress.getTitle().isEmpty() ||
                        progress.getProjectName() == null || progress.getProjectName().isEmpty()) {
                    throw new IllegalArgumentException("Title and project name are required for project template");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid template type");
        }

        // Set default values
        progress.setCreatedAt(new Date());
        progress.setUpdatedAt(new Date());
        progress.setLikes(new ArrayList<>());
        progress.setComments(new ArrayList<>());
        return learningProgressRepository.save(progress);
    }

    /**
     * Retrieves all learning progress entries sorted by creation date (newest first)
     * @return List of all learning progress entries
     */
    public List<LearningProgress> getAllLearningProgress() {
        return learningProgressRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Retrieves a specific learning progress entry by ID
     * @param id The ID of the entry to retrieve
     * @return The found learning progress entry
     * @throws NoSuchElementException if entry not found
     */
    public LearningProgress getLearningProgressById(String id) {
        return learningProgressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Learning progress not found"));
    }

    /**
     * Retrieves all learning progress entries for a specific user
     * @param userId The ID of the user
     * @return List of user's learning progress entries
     */
    public List<LearningProgress> getLearningProgressByUserId(String userId) {
        return learningProgressRepository.findByUserId(userId);
    }

    /**
     * Updates an existing learning progress entry
     * @param id The ID of the entry to update
     * @param progressDetails The updated progress data
     * @return The updated learning progress entry
     */
    public LearningProgress updateLearningProgress(String id, LearningProgress progressDetails) {
        LearningProgress progress = getLearningProgressById(id);
        // Update all editable fields
        progress.setTitle(progressDetails.getTitle());
        progress.setDescription(progressDetails.getDescription());
        progress.setTemplateType(progressDetails.getTemplateType());
        progress.setStatus(progressDetails.getStatus());
        progress.setTutorialName(progressDetails.getTutorialName());
        progress.setProjectName(progressDetails.getProjectName());
        progress.setSkillsLearned(progressDetails.getSkillsLearned());
        progress.setChallenges(progressDetails.getChallenges());
        progress.setNextSteps(progressDetails.getNextSteps());
        progress.setUpdatedAt(new Date());
        return learningProgressRepository.save(progress);
    }

    /**
     * Deletes a learning progress entry
     * @param id The ID of the entry to delete
     */
    public void deleteLearningProgress(String id) {
        LearningProgress progress = getLearningProgressById(id);
        learningProgressRepository.delete(progress);
    }

    /**
     * Adds a comment to a learning progress entry
     * @param entryId The ID of the progress entry
     * @param comment The comment to add
     * @return The updated learning progress entry
     */
    public LearningProgress addComment(String entryId, Comment comment) {
        LearningProgress progress = getLearningProgressById(entryId);
        if (progress.getComments() == null) {
            progress.setComments(new ArrayList<>());
        }
        // Set default values for new comment
        if (comment.getUserName() == null || comment.getUserName().isEmpty()) {
            comment.setUserName("Unknown User");
        }
        comment.setId(UUID.randomUUID().toString());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());
        progress.getComments().add(comment);
        
        // Send notification if commenter is not the entry owner
        if (!progress.getUserId().equals(comment.getUserId())) {
            notificationService.createCommentNotification(entryId, progress.getUserId(), comment.getUserId(),
                    comment.getContent());
        }
        return learningProgressRepository.save(progress);
    }

    /**
     * Updates an existing comment on a learning progress entry
     * @param entryId The ID of the progress entry
     * @param commentId The ID of the comment to update
     * @param commentDetails The updated comment data
     * @return The updated learning progress entry
     */
    public LearningProgress updateComment(String entryId, String commentId, Comment commentDetails) {
        LearningProgress progress = getLearningProgressById(entryId);
        progress.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .ifPresent(c -> {
                    c.setContent(commentDetails.getContent());
                    c.setUpdatedAt(new Date());
                });
        return learningProgressRepository.save(progress);
    }

    /**
     * Deletes a comment from a learning progress entry
     * @param entryId The ID of the progress entry
     * @param commentId The ID of the comment to delete
     * @param userId The ID of the user requesting deletion
     * @return The updated learning progress entry
     */
    public LearningProgress deleteComment(String entryId, String commentId, String userId) {
        LearningProgress progress = getLearningProgressById(entryId);
        boolean isOwner = progress.getUserId().equals(userId);
        // Filter out the comment if user is owner or comment author
        progress.setComments(progress.getComments().stream()
                .filter(c -> !(c.getId().equals(commentId) && (c.getUserId().equals(userId) || isOwner)))
                .collect(Collectors.toList()));
        return learningProgressRepository.save(progress);
    }

    /**
     * Adds a like to a learning progress entry
     * @param entryId The ID of the progress entry
     * @param like The like to add
     * @return The updated learning progress entry
     */
    public LearningProgress addLike(String entryId, Like like) {
        LearningProgress progress = getLearningProgressById(entryId);
        if (progress.getLikes() == null) {
            progress.setLikes(new ArrayList<>());
        }
        // Check for existing like from same user
        boolean alreadyLiked = progress.getLikes().stream()
                .anyMatch(l -> l.getUserId().equals(like.getUserId()));

        if (!alreadyLiked) {
            like.setCreatedAt(new Date());
            progress.getLikes().add(like);
            // Send notification if liker is not the entry owner
            if (!progress.getUserId().equals(like.getUserId())) {
                notificationService.createLikeNotification(entryId, progress.getUserId(), like.getUserId());
            }
            return learningProgressRepository.save(progress);
        }
        return progress;
    }

    /**
     * Removes a like from a learning progress entry
     * @param entryId The ID of the progress entry
     * @param userId The ID of the user whose like to remove
     * @return The updated learning progress entry
     */
    public LearningProgress removeLike(String entryId, String userId) {
        LearningProgress progress = getLearningProgressById(entryId);
        // Filter out the user's like
        progress.setLikes(progress.getLikes().stream()
                .filter(like -> !like.getUserId().equals(userId))
                .collect(Collectors.toList()));
        return learningProgressRepository.save(progress);
    }
}