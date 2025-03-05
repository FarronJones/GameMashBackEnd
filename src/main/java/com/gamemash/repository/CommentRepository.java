package com.gamemash.repository;

import com.gamemash.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByUserId(Long userId);
}