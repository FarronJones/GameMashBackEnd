package com.gamemash.controller;

import com.gamemash.entity.Comment;
import com.gamemash.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*") // allow frontend
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody Comment comment) {
        comment.setMessageType("community");
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment saved");
    }

    @GetMapping("/community")
    public ResponseEntity<List<Comment>> getCommunityComments() {
        List<Comment> comments = commentRepository.findByMessageType("community");
        return ResponseEntity.ok(comments);
    }
}