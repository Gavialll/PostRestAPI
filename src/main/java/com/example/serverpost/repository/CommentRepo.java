package com.example.serverpost.repository;

import com.example.serverpost.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> getAllByPostId(Long id);

}
