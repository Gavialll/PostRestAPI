package com.example.serverpost.service.impl;

import com.example.serverpost.repository.CommentRepo;
import com.example.serverpost.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService implements com.example.serverpost.service.CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Override
    public Comment get(Long id) {
        return commentRepo.getById(id);
    }

    @Override
    public Comment add(Comment comment) {
        comment.setDate(LocalDateTime.now());
        return commentRepo.save(comment);
    }

    @Override
    public String delete(Long id) {
        commentRepo.deleteById(id);
        return "Delete Comment Complete";
    }

    @Override
    public Comment update(Long id, Comment comment) {
        comment.setDate(LocalDateTime.now());
        comment.setId(id);
        return commentRepo.save(comment);
    }

    @Override
    public List<Comment> getAllComment(Long id) {
        return commentRepo.getAllByPostId(id);
    }
}
