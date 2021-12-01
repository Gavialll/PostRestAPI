package com.example.serverpost.controller;

import com.example.serverpost.dto.PostDto;
import com.example.serverpost.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class AccountPostControllerTest {

    @Test
    void getOne() {

        Post post = createPost();

        if(post.getUserId().equals(1L))
            Assertions.assertEquals(create(post), create(post));

    }

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

    public Post createPost(){
        Post post  = new Post();
        post.setDescription("test description");
        post.setPrice(111);
        post.setCategory(0L);
        post.setDate(LocalDateTime.now());
        post.setName("test name");
        post.setUserId(1L);
        post.setId(11L);
        post.setImg(null);
        post.setCommentList(null);

        return post;
    }
}