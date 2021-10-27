package com.example.serverpost.controller;

import com.example.serverpost.dto.PostDto;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/post")
public class AnonymousPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;


    //коменти до поста
    @GetMapping("/{postId}/comment")
    public List<Comment> getAllComment(@PathVariable Long postId) {
        return commentService.getAllComment(postId);
    }

    //всі пости
    @GetMapping
    public List<PostDto> getAllPost(){
        return PostDto.create(postService.getAllPost());
    }

    //пост по id
    @GetMapping("/{id}")
    public PostDto get(@PathVariable Long id) throws PostException {
        return PostDto.create(postService.get(id));
    }

    //фото до поста
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
}
