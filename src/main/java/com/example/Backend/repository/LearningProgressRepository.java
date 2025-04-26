// Package declaration for the repository layer
package com.example.Backend.repository;

// Import required classes and interfaces
import com.example.Backend.model.LearningProgress;
import com.example.Backend.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for LearningProgress entities.
 * Extends MongoRepository to provide CRUD operations for LearningProgress documents.
 * Uses String as the type for document ID.
 */
@Repository // Marks this interface as a Spring Data repository component
public interface LearningProgressRepository extends MongoRepository<LearningProgress, String> {

    /**
     * Finds all LearningProgress documents by user ID.
     * @param userId The ID of the user to search for
     * @return List of LearningProgress documents for the specified user
     */
    List<LearningProgress> findByUserId(String userId);

    /**
     * Finds all LearningProgress documents, ordered by creation date in descending order.
     * @return List of all LearningProgress documents, newest first
     */
    List<LearningProgress> findAllByOrderByCreatedAtDesc();

    /**
     * Finds all LearningProgress documents for a specific user,
     * ordered by creation date in descending order.
     * @param userId The ID of the user to search for
     * @return List of LearningProgress documents for the specified user, newest first
     */
    List<LearningProgress> findByUserIdOrderByCreatedAtDesc(String userId);
}