package com.example.serverpost.dao;

import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> getAllByPostId(Long id);

}
