package com.example.serverpost.controller;

import com.example.serverpost.exception.PostException;
import com.example.serverpost.exception.UserException;
import com.example.serverpost.model.Post;
import com.example.serverpost.model.User;
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
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    // post controller /////////////////////////////
    @PostMapping("/post")
    public Post add(@RequestBody Post post) {
        return postService.add(post);
    }

//    @PostMapping(value = "/post/{id}/img")
//    public boolean addFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws PostException {
//        Path p = Paths.get(Url.post);
//        Post post = postService.get(id);
//        post.setImg(FileService.save(file, p));
//        postService.update(post.getId(), post);
//        return true;
//    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable Long id) {
        return postService.delete(id);
    }

    @PutMapping("/post/{id}")
    public Post update(@PathVariable Long id, @RequestBody Post post) {
        return postService.update(id, post);
    }


    // user controller ////////////////////////////
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) throws UserException {
        userService.update(id, user);
        return userService.get(id);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.delete(id);
        return "User Delete Ok";
    }

    @PostMapping("/user/{id}/post")
    public List<Post> addPostToUser(@PathVariable Long id, @RequestBody Post post){
        post.setUserId(id);
        post.setDate(LocalDateTime.now());
        //потрібно додати null  або перезаписати пост через new
        postService.add(post);
        return userService.getAllPost(id);
    }

    @PostMapping("/user/{id}/img")
    public Boolean addImg(@PathVariable Long id, @RequestParam MultipartFile file) throws UserException {
        Path path = Paths.get(Url.user);
        User user = userService.get(id);
        user.setImg(FileService.save(file, path));
        userService.update(id, user);
        return true;
    }
}
