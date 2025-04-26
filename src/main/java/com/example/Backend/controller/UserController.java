package com.example.Backend.controller;

import com.example.Backend.dto.ProfileUpdateDTO;
import com.example.Backend.dto.UserProfileDTO;
import com.example.Backend.dto.UserUpdateDTO;
import com.example.Backend.model.User;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> ResponseEntity.ok(userService.convertToProfileDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable String id) {
        return userService.getUserProfile(id);
    }

    @GetMapping("/batch")
    public ResponseEntity<List<UserProfileDTO>> getUsersByIds(@RequestParam List<String> ids) {
        List<UserProfileDTO> users = userService.getUsersByIds(ids);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = existingUser.get();

        // Check if email is provided and already in use by another user
        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().trim().isEmpty()) {
            Optional<User> userWithEmail = userRepository.findByEmail(userUpdateDTO.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use");
            }
            user.setEmail(userUpdateDTO.getEmail());
        }

        // Update name if provided
        if (userUpdateDTO.getName() != null && !userUpdateDTO.getName().trim().isEmpty()) {
            user.setName(userUpdateDTO.getName());
        }

        // Save updated user
        try {
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(userService.convertToProfileDTO(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user");
        }
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String id, @Valid @RequestBody ProfileUpdateDTO profileDTO) {
        return userService.updateUserProfile(id, profileDTO);
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<?> followUser(@PathVariable String id, @RequestParam String followerId) {
        return userService.followUser(id, followerId);
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<?> unfollowUser(@PathVariable String id, @RequestParam String followerId) {
        return userService.unfollowUser(id, followerId);
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<?> getUserContent(@PathVariable String id) {
        return userService.getUserContent(id);
    }

    @GetMapping("/{userId}/post/count")
    public ResponseEntity<?> getUserTotalPostCount(@PathVariable String userId){
        return ResponseEntity.ok().body(userService.getUserTotalPostCount(userId));
    }
}