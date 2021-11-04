package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.dto.PostDto;
import com.example.serverpost.exception.PostException;
import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.FileService;
import com.example.serverpost.service.PostService;
import com.example.serverpost.service.Url;
import com.example.serverpost.service.UserService;
import com.example.serverpost.service.impl.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/account/post")
@Api(description = "Контролер для управління публікаціями користувача")
public class AccountPostController {
    private final UserService userService;
    private final PostService postService;
    private final AuthenticationUser authentication;
    private final CommentService commentService;

    public AccountPostController(UserService userService, PostService postService, AuthenticationUser authentication, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.authentication = authentication;
        this.commentService = commentService;
    }

    @GetMapping
    @ApiOperation("Всі публікації користувача")
    public List<PostDto> getAllPost(@RequestParam(name = "id", required = false) Long postId) throws PostException {

        if(postId == null) return PostDto.create(userService.getAllPost(authentication.Id()));
        else {
            Post post = postService.get(postId);
            if(post.getUserId().equals(authentication.Id())) return List.of(PostDto.create(post));
        }
        return null;
    }

    @PostMapping
    @ApiOperation("Додаєм нову публікацію")
    public PostDto addPostToUser(@RequestBody Post post){
        post.setUserId(authentication.Id());
        post.setDate(LocalDateTime.now());
//        postService.add(post);
        return PostDto.create(postService.add(post));
    }

    @PostMapping("/{id}/img")
    @ApiOperation("Додаєм головне фото публікації")
    public boolean addFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws PostException {
        Path p = Paths.get(Url.post);
        Post post = postService.get(id);
        post.setImg(FileService.save(file, p));
        postService.update(post.getId(), post);
        return true;
    }

    @PostMapping("/{id}/comment")
    @ApiOperation("Додаєм коментар до публікації від імені авторизованого користувача")
    public boolean addCommentToPost(@RequestBody String message, @PathVariable Long id){
        try {
            Comment comment = new Comment();
            comment.setSenderId(authentication.Id());
            comment.setPostId(id);
            comment.setDate(LocalDateTime.now());
            comment.setMessage(message);

            System.out.println(message);

            commentService.add(comment);

        }catch(Exception e){
            return false;
        }
        return true;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Видалити публікацію")
    public boolean delete(@PathVariable Long id){
        postService.delete(id);
        return true;
    }
}
