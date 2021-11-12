package com.example.serverpost.service.impl;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.model.Comment;
import com.example.serverpost.repository.CommentRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements com.example.serverpost.service.CommentService {
    private final CommentRepo commentRepo;
    private final AuthenticationUser authentication;

    public CommentServiceImpl(CommentRepo commentRepo, AuthenticationUser authentication) {
        this.commentRepo = commentRepo;
        this.authentication = authentication;
    }

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
    public void delete(Long id) {
        commentRepo.deleteById(id);
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

    public Comment create(String message, Long postId){
        Comment comment = new Comment();
        comment.setSenderId(authentication.Id());
        comment.setPostId(postId);
        comment.setDate(LocalDateTime.now());
        comment.setMessage(message);
        return comment;
    }
}
