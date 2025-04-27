package com.example.Backend.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a learning progress entity stored in MongoDB.
 * Tracks user's learning activities, progress status, and related information.
 */
@Document(collection = "learning_progress")  // MongoDB collection name
@AllArgsConstructor  // Lombok: Generates constructor with all arguments
@NoArgsConstructor   // Lombok: Generates no-args constructor
public class LearningProgress {
    @Id  // Marks this field as the MongoDB document ID
    private String id;
    
    // User information
    private String userId;       // ID of the user who created this progress entry
    private String userName;     // Name of the user
    
    // Progress metadata
    private String title;        // Title of the learning progress
    private String description;  // Detailed description
    private String templateType; // Type/category of the progress template
    private String status;       // Current status (e.g., "In Progress", "Completed")
    
    // Learning-specific details
    private String tutorialName;   // Name of tutorial/course being followed
    private String projectName;    // Name of related project
    private String skillsLearned;  // Skills acquired through this progress
    private String challenges;     // Challenges faced during learning
    private String nextSteps;      // Planned next steps in learning journey
    
    // Timestamps
    private Date createdAt;    // When this progress entry was created
    private Date updatedAt;    // When this progress was last updated
    
    // Social interaction features
    private List<Like> likes;       // List of likes/reactions to this progress
    private List<Comment> comments; // Comments on this progress entry

    // ========== GENERATED GETTERS AND SETTERS ========== //
    // (Below are standard accessor methods for all fields) //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTutorialName() {
        return tutorialName;
    }

    public void setTutorialName(String tutorialName) {
        this.tutorialName = tutorialName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSkillsLearned() {
        return skillsLearned;
    }

    public void setSkillsLearned(String skillsLearned) {
        this.skillsLearned = skillsLearned;
    }

    public String getChallenges() {
        return challenges;
    }

    public void setChallenges(String challenges) {
        this.challenges = challenges;
    }

    public String getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(String nextSteps) {
        this.nextSteps = nextSteps;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}