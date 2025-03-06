package com.gamemash.controller;

import com.gamemash.entity.Comments;
import com.gamemash.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // Get all comments
    @GetMapping
    public List<Comments> getAllComments() {
        return commentRepository.findAll();
    }

    // Get comments by user ID
    @GetMapping("/user/{userId}")
    public List<Comments> getCommentsByUserId(@PathVariable Long userId) {
        return commentRepository.findByUserId(userId);
    }

    // Create a new comment
    @PostMapping
    public Comments createComment(@RequestBody Comments comment) {
        comment.setDatePosted(LocalDate.now()); // Sets current date
        comment.setTimeStamp(LocalDateTime.now()); // Sets current timestamp
        return commentRepository.save(comment);
    }

    // Delete comment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}