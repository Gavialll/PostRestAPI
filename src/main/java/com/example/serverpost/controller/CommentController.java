package com.example.serverpost.controller;

import com.example.serverpost.model.Comment;
import com.example.serverpost.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PutMapping("api/comment/{id}")
    public Comment update(@PathVariable Long id, @RequestBody Comment comment){
        return commentService.update(id, comment);
    }

    @DeleteMapping("api/comment/{id}")
    public String delete(@PathVariable Long id){
        return commentService.delete(id);
    }
}
