package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.exception.PostException;
import com.example.serverpost.exception.UserException;
import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import com.example.serverpost.model.User;
import com.example.serverpost.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @GetMapping()
    public User getUser() throws UserException {
        return userService.get(AuthenticationUser.Id(userService));
    }

    @GetMapping("/id")
    public Long getId(){
        return AuthenticationUser.getIdAccount();
    }

    //додати комент до поста
    @PostMapping("/{postId}/comment")
    public Comment addComment(@PathVariable Long postId, @RequestBody Comment comment){
        comment.setPostId(postId);
        comment.setSenderId(AuthenticationUser.Id(userService));
        return commentService.add(comment);
    }

    @PostMapping("/img")
    public Boolean addImg(@RequestParam MultipartFile file) throws UserException {
        Path path = Paths.get(Url.user);
        User user = userService.get(AuthenticationUser.Id(userService));


        // при зміні фото профіля видаляєм старе фото
        if(user.getImg() != null){
            File photo = new File(Url.user + user.getImg());
            if(photo.delete()) System.out.println("true photo delete");
            else System.out.println("false photo delete");
        }

        user.setImg(FileService.save(file, path));
        userService.update(AuthenticationUser.Id(userService), user);
        return true;
    }

    @GetMapping("/img")
    public ResponseEntity getImg() throws IOException, UserException {
        BufferedImage bufferedImage = ImageIO
                .read(FileService.getFile(userService.get(AuthenticationUser.Id(userService)).getImg(), Url.user));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) throws UserException {
        userService.update(AuthenticationUser.Id(userService), user);
        return userService.get(AuthenticationUser.Id(userService));
    }

    @DeleteMapping()
    public String deleteUser(){
        userService.delete(AuthenticationUser.Id(userService));
        return "User Delete Ok";
    }

    @GetMapping("/post")
    public List<Post> getAllPost(
            @RequestParam(name = "id", required = false) Long postId) throws PostException {
        if(postId == null) return userService.getAllPost(AuthenticationUser.Id(userService));
        else {
            Post post = postService.get(postId);
            if(post.getUserId().equals(AuthenticationUser.Id(userService))) return List.of(post);
        }
        return null;
    }

    @PostMapping("/post/add")
    public List<Post> addPostToUser(@RequestBody Post post){
        post.setUserId(AuthenticationUser.Id(userService));
        post.setDate(LocalDateTime.now());
        //потрібно додати null  або перезаписати пост через new
        postService.add(post);
        return userService.getAllPost(AuthenticationUser.Id(userService));
    }

    @PostMapping("post/{id}/img")
    public boolean addFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws PostException {
        Path p = Paths.get(Url.post);
        Post post = postService.get(id);
        post.setImg(FileService.save(file, p));
        postService.update(post.getId(), post);
        return true;
    }

    @GetMapping("post/{id}/img")
    public ResponseEntity getFile(@PathVariable Long id) throws IOException, PostException {
        BufferedImage bufferedImage = ImageIO
                .read(FileService.getFile(postService.get(id).getImg(), Url.post));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}