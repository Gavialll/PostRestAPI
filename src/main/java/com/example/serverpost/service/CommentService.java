package com.example.serverpost.service;

import com.example.serverpost.model.Comment;

import java.util.List;

public interface CommentService {
    Comment add(Comment comment);
    List<Comment> getAllComment(Long id);
}
