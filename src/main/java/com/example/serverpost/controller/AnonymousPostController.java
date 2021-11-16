package com.example.serverpost.controller;

import com.example.serverpost.component.FileService;
import com.example.serverpost.dto.PostDto;
import com.example.serverpost.model.Comment;
import com.example.serverpost.service.CommentService;
import com.example.serverpost.service.PostService;
import com.example.serverpost.service.Url;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/post")
@Api(description = "Контролер публікації для анонімних користувачів")
public class AnonymousPostController {
    private final PostService postService;
    private final CommentService commentService;

    public AnonymousPostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/{postId}/comment")
    @ApiOperation("Всі коментарі до публікації")
    public List<Comment> getAllComment(@PathVariable Long postId) {
        if(postService.get(postId) != null) {
            List<Comment> commentList = commentService.getAllComment(postId);
            commentList.sort(Comparator.comparing(Comment::getDate));
            Collections.reverse(commentList);
            return commentList;
        }
        return null;
    }

    @GetMapping
    @ApiOperation("Всі публікації")
    public List<PostDto> getAllPost(){
        return PostDto.create(postService.getAllPost());
    }

    @GetMapping("/{id}")
    @ApiOperation("Публікація по ID")
    public PostDto get(@PathVariable Long id){
        return PostDto.create(postService.get(id));
    }

    @GetMapping("{id}/img")
    @ApiOperation("Фото до публікації")
    public ResponseEntity getImage(@PathVariable Long id) throws IOException {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(FileService.getFile(postService.get(id).getImg(), Url.post));
        }catch(IIOException exception){
            bufferedImage = ImageIO.read(FileService.getFile("defaultPost.jpg", Url.defaultImg));
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(baos.toByteArray());
    }
}
