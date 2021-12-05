package com.example.serverpost.service.impl;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.dto.AddPostDto;
import com.example.serverpost.exception.post.PostNotFoundException;
import com.example.serverpost.model.Post;
import com.example.serverpost.repository.PostRepo;
import com.example.serverpost.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;

@SpringBootTest
class PostServiceImplTest {

    @MockBean
    private PostRepo postRepo;

    @MockBean
    private AuthenticationUser authentication;

    @Mock
    private Post post;

    @Autowired
    private PostService postService;

    @BeforeEach
    public void before(){
        Mockito.when(post.getName()).thenReturn("post name");
    }

    @Test
    void get() {
        Mockito.when(postRepo.findById(32L)).thenReturn(Optional.of(post));
        Mockito.when(postRepo.findById(33L)).thenThrow(PostNotFoundException.class);

        assertEquals(post.getName(), postService.get(32L).getName());
        assertThrows(PostNotFoundException.class, () -> postService.get(33L));
    }

    @Test
    void add() {
        Mockito.when(postRepo.save(any(Post.class))).thenReturn(post);
        Mockito.when(authentication.Id()).thenReturn(1L);

        Post post = postService.add(new AddPostDto("post name", "description", 111, 0L));
        assertEquals(post.getName(), "post name");
    }
}