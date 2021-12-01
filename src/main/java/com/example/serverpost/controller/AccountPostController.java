package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.component.FileService;
import com.example.serverpost.dto.AddPostDto;
import com.example.serverpost.dto.PostDto;
import com.example.serverpost.exception.post.PostImageException;
import com.example.serverpost.exception.post.PostNotFoundException;
import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.Url;
import com.example.serverpost.service.UserService;
import com.example.serverpost.service.impl.CommentServiceImpl;
import com.example.serverpost.service.impl.PostServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/account/post")
@Api(tags = "Контролер для управління публікаціями користувача")
@Slf4j
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
        else{
            log.warn("Post not found, id = " + id);
            throw new PostNotFoundException("Post not found, id = " + id);
        }
    }

    @PostMapping
    @ApiOperation("Додаєм нову публікацію")
    public PostDto addPost(@RequestBody AddPostDto addPostDto){

        return PostDto.create(postService.add(addPostDto));
    }
    
    @PostMapping("/{id}/img")
    @ApiOperation("Додаєм головне фото публікації")
    public HttpStatus addImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {

        Path path = Paths.get(Url.post);
        Post post = postService.get(id);

        if(post.getImg() != null){
            File photo = new File(Url.post + post.getImg());
            if(!photo.delete()) {
                log.warn("The previous image was not deleted");
                throw new PostImageException("The previous image was not deleted");
            }
        }

        post.setImg(FileService.save(file, path));
        postService.update(post.getId(), post);

        return HttpStatus.OK;
    }

    @PostMapping("/{id}/comment")
    @ApiOperation("Додаєм коментар до публікації від імені авторизованого користувача")
    public Comment addCommentToPost(@RequestBody String message, @PathVariable Long id){

        Comment comment = new Comment();
        comment.setSenderId(authentication.Id());
        comment.setPostId(id);
        comment.setDate(LocalDateTime.now());
        comment.setMessage(message);

        return commentService.add(comment);
    }

    @PutMapping("/{id}")
    @ApiOperation("Редагування публікації")
    public PostDto update(@PathVariable Long id, @RequestBody AddPostDto addPostDto){

        Post post = postService.get(id);

        if(addPostDto.getName() != null) post.setName(addPostDto.getName());
        if(addPostDto.getCategory() != null) post.setCategory(addPostDto.getCategory());
        if(addPostDto.getPrice() != null) post.setPrice(addPostDto.getPrice());
        if(addPostDto.getDescription() != null) post.setDescription(addPostDto.getDescription());

        return PostDto.create(postService.update(id, post));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Видалити публікацію")
    public HttpStatus delete(@PathVariable Long id){

        Post post = postService.get(id);
        File photo = new File(Url.post + post.getImg());

        if(!photo.delete()) {
            log.warn("Image not deleted");
            throw new PostImageException("Image not deleted");
        }
        postService.delete(id);

        return HttpStatus.OK;
    }
}
