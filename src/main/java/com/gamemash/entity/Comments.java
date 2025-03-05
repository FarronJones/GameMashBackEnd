package com.gamemash.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")  // Auto-incremented primary key
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "DatePosted")
    private LocalDate datePosted;

    @Column(name = "TimeStamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime timeStamp;

    @Column(name = "Comment", columnDefinition = "TEXT", nullable = false)
    private String comment;

    public Comments() {}

    public Comments(Long userId, LocalDate datePosted, LocalDateTime timeStamp, String comment) {
        this.userId = userId;
        this.datePosted = datePosted;
        this.timeStamp = timeStamp;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDate getDatePosted() { return datePosted; }
    public void setDatePosted(LocalDate datePosted) { this.datePosted = datePosted; }

    public LocalDateTime getTimeStamp() { return timeStamp; }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}