package com.example.serverpost.service;

import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;

import java.util.List;

public interface CommentService {
    Comment get(Long id);
    Comment add(Comment comment);
    String delete(Long id);
    Comment update(Long id, Comment comment);
    List<Comment> getAllComment(Long id);
}
