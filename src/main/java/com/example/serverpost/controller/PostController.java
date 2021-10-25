package com.example.serverpost.controller;

import com.example.serverpost.exception.PostException;
import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.CommentService;
import com.example.serverpost.service.FileService;
import com.example.serverpost.service.PostService;
import com.example.serverpost.service.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/{postId}/comment")
    public List<Comment> getAllComment(@PathVariable Long postId) {
        return commentService.getAllComment(postId);
    }

    @GetMapping
    public List<Post> getAllPost(){
        return postService.getAllPost();
    }

    @GetMapping("/{id}")
    public Post get(@PathVariable Long id) throws PostException {
        return postService.get(id);
    }

    @GetMapping("{id}/img")
    public ResponseEntity getImage(@PathVariable Long id) throws PostException, IOException {
        BufferedImage bufferedImage = ImageIO
                .read(FileService.getFile(postService.get(id).getImg(), Url.post));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }

//    @PostMapping(value = "/{id}/img")
//    public boolean addFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws PostException {
//        Path p = Paths.get(Url.post);
//        Post post = postService.get(id);
//        post.setImg(FileService.save(file, p));
//        postService.update(post.getId(), post);
//        return true;
//    }
}
