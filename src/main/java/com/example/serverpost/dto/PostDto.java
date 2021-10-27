package com.example.serverpost.dto;

import com.example.serverpost.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long userId;
    private LocalDateTime date;
    private Long category;
    private String name;
    private Integer price;
    private String description;

    public static PostDto create(Post post){
        return new PostDto(
                post.getId(),
                post.getUserId(),
                post.getDate(),
                post.getCategory(),
                post.getName(),
                post.getPrice(),
                post.getDescription());
    }

    public static List<PostDto> create(List<Post> postList){
        List<PostDto> postDtoList = new ArrayList<>();
        postList.forEach(post -> postDtoList.add(PostDto.create(post)));
        return postDtoList;
    }
}
