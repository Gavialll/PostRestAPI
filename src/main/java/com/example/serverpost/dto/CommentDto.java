package com.example.serverpost.dto;

import com.example.serverpost.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class CommentDto {
    private String message;
    private Long senderId;
    private LocalDateTime date;

    public static CommentDto create(Comment comment){
        return new CommentDto(
                comment.getMessage(),
                comment.getSenderId(),
                comment.getDate());
    }
}
