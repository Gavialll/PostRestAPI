package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.dto.PostDto;
import com.example.serverpost.exception.PostException;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.FileService;
import com.example.serverpost.service.PostService;
import com.example.serverpost.service.Url;
import com.example.serverpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/account/post")
public class AccountPostController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private AuthenticationUser authentication;

    //Получаєм всі пости авторизованого користувача
    @GetMapping
    public List<PostDto> getAllPost(
            @RequestParam(name = "id", required = false) Long postId) throws PostException {

        if(postId == null) return PostDto.create(userService.getAllPost(authentication.Id()));
        else {
            Post post = postService.get(postId);
            if(post.getUserId().equals(authentication.Id())) return List.of(PostDto.create(post));
        }
        return null;
    }

    @PostMapping
    public List<PostDto> addPostToUser(@RequestBody Post post){
        post.setUserId(authentication.Id());
        post.setDate(LocalDateTime.now());
        postService.add(post);
        return PostDto.create(userService.getAllPost(authentication.Id()));
    }

    @PostMapping("/{id}/img")
    public boolean addFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws PostException {
        Path p = Paths.get(Url.post);
        Post post = postService.get(id);
        post.setImg(FileService.save(file, p));
        postService.update(post.getId(), post);
        return true;
    }
}
