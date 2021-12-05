package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.dto.AddPostDto;
import com.example.serverpost.exception.post.PostNotFoundException;
import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.CommentService;
import com.example.serverpost.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;

@SpringBootTest
class AccountPostControllerTest {

    @Autowired
    private AccountPostController postController;

    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private AuthenticationUser authentication;

    @Test
    void getOne() {
        Post post = new Post();
        post.setName("get one post");
        post.setUserId(1L);
        Mockito.when(postService.get(12L)).thenReturn(post);
        Mockito.when(authentication.Id()).thenReturn(2L);

        assertThrows(PostNotFoundException.class, () -> postController.getOne(12L));

        post.setUserId(2L);
        assertEquals("get one post", postController.getOne(12L).getName());
    }

    @Test
    void addCommentToPost() {
        Comment comment = new Comment();
        comment.setSenderId(1L);
        comment.setPostId(21L);
        comment.setMessage("message");
        Mockito.when(commentService.add(any(Comment.class))).thenReturn(comment);
        Comment comment1 = postController.addCommentToPost("message", 21L);

        assertEquals("message", comment1.getMessage());

    }

    @Test
    void update() {
        Post postOk = new Post();
        postOk.setName("name");
        postOk.setDescription("description");
        postOk.setCategory(0L);
        postOk.setPrice(100);
        Mockito.when(postService.get(10L)).thenReturn(postOk);
        Mockito.when(postService.update(10L, postOk)).thenReturn(postOk);

        assertEquals("name", postController.update(10L, new AddPostDto()).getName());
        assertEquals(100, postController.update(10L, new AddPostDto()).getPrice());
        assertEquals("description", postController.update(10L, new AddPostDto()).getDescription());


        Mockito.when(postService.get(11L)).thenReturn(postOk);
        Mockito.when(postService.update(11L, postOk)).thenReturn(postOk);

        AddPostDto addPostDto = new AddPostDto();
        addPostDto.setPrice(150);
        addPostDto.setDescription("description update");
        addPostDto.setName("name update");

        assertEquals("name update", postController.update(11L, addPostDto).getName());
        assertEquals(150, postController.update(11L, addPostDto).getPrice());
        assertEquals("description update", postController.update(11L, addPostDto).getDescription());
    }

}