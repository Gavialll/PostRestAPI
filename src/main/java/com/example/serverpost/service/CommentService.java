package com.example.serverpost.service;

import com.example.serverpost.model.Comment;

import java.util.List;

public interface CommentService {
    Comment get(Long id);
    Comment add(Comment comment);
    void delete(Long id);
    Comment update(Long id, Comment comment);
    List<Comment> getAllComment(Long id);
}
