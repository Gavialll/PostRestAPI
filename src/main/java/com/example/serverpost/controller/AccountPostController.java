package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.component.FileService;
import com.example.serverpost.dto.AddPostDto;
import com.example.serverpost.dto.PostDto;
import com.example.serverpost.exception.post.PostNotFoundException;
import com.example.serverpost.exception.post.PostPriceException;
import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.Url;
import com.example.serverpost.service.UserService;
import com.example.serverpost.service.impl.CommentServiceImpl;
import com.example.serverpost.service.impl.PostServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/account/post")
@Api(description = "Контролер для управління публікаціями користувача")
public class AccountPostController {
    private final UserService userService;
    private final PostServiceImpl postService;
    private final AuthenticationUser authentication;
    private final CommentServiceImpl commentService;

    public AccountPostController(UserService userService, PostServiceImpl postService, AuthenticationUser authentication, CommentServiceImpl commentService) {
        this.userService = userService;
        this.postService = postService;
        this.authentication = authentication;
        this.commentService = commentService;
    }

    @GetMapping
    @ApiOperation("Всі публікації користувача")
    public List<PostDto> getAll(){
        return PostDto.create(userService.getAllPost(authentication.Id()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Дістаєм публікацію користувача по ID")
    public PostDto getOne(@PathVariable Long id){
        Post post = postService.get(id);
        if(post.getUserId().equals(authentication.Id())) return PostDto.create(post);
        else throw new PostNotFoundException("Post not found, id = " + id);
    }

    @PostMapping
    @ApiOperation("Додаєм нову публікацію")
    public PostDto addPost(@RequestBody AddPostDto addPostDto){

        if(addPostDto.getPrice() < 0) throw new PostPriceException("Price no validation");
        // TODO: 12.11.2021 Валідація полів 
        Post post = postService.create(addPostDto);
        return PostDto.create(postService.add(post));
    }
    
    @PostMapping("/{id}/img")
    @ApiOperation("Додаєм головне фото публікації")
    public HttpStatus addImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Path p = Paths.get(Url.post);
        Post post = postService.get(id);
        post.setImg(FileService.save(file, p));
        postService.update(post.getId(), post);
        return HttpStatus.OK;
    }

    @PostMapping("/{id}/comment")
    @ApiOperation("Додаєм коментар до публікації від імені авторизованого користувача")
    public Comment addCommentToPost(@RequestBody String message, @PathVariable Long id){
        Comment comment = commentService.create(message, id);
        return commentService.add(comment);
    }

    @PutMapping("/{id}")
    @ApiOperation("Редагування публікації")
    public PostDto update(@PathVariable Long id, @RequestBody AddPostDto addPostDto){
        Post post = postService.get(id);
        post.setName(addPostDto.getName());
        post.setCategory(addPostDto.getCategory());
        post.setPrice(addPostDto.getPrice());
        post.setDescription(addPostDto.getDescription());
        return PostDto.create(postService.update(id, post));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Видалити публікацію")
    public HttpStatus delete(@PathVariable Long id){
        postService.delete(id);
        return HttpStatus.OK;
    }
}
