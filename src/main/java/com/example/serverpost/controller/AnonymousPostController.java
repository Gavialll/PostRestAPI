package com.example.serverpost.controller;

import com.example.serverpost.dto.PostDto;
import com.example.serverpost.exception.PostException;
import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.CommentService;
import com.example.serverpost.service.FileService;
import com.example.serverpost.service.PostService;
import com.example.serverpost.service.Url;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/post")
@Api(description = "Контролер публікації для анонімних користувачів")
public class AnonymousPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/{postId}/comment")
    @ApiOperation("Всі коментарі до публікації")
    public List<Comment> getAllComment(@PathVariable Long postId) {
        List<Comment> commentList = commentService.getAllComment(postId);
        commentList.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        Collections.reverse(commentList);
        return commentList;
    }

    @GetMapping
    @ApiOperation("Всі публікації")
    public List<PostDto> getAllPost(){
        return PostDto.create(postService.getAllPost());
    }


    @GetMapping("/{id}")
    @ApiOperation("Публікація по ID")
    public PostDto get(@PathVariable Long id) throws PostException {
        return PostDto.create(postService.get(id));
    }

    @GetMapping("{id}/img")
    @ApiOperation("Фото до публікації")
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
