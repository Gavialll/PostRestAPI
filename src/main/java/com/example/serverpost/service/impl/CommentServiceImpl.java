package com.example.serverpost.service.impl;

import com.example.serverpost.model.Comment;
import com.example.serverpost.repository.CommentRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements com.example.serverpost.service.CommentService {

    private final CommentRepo commentRepo;

    public CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public Comment add(Comment comment) {
        comment.setDate(LocalDateTime.now());
        return commentRepo.save(comment);
    }

    @Override
    public List<Comment> getAllComment(Long id) {
        return commentRepo.getAllByPostId(id);
    }
}
