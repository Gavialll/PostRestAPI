package com.example.serverpost.controller;

import com.example.serverpost.component.FileService;
import com.example.serverpost.dto.PostDto;
import com.example.serverpost.exception.post.PostNotFoundException;
import com.example.serverpost.model.Comment;
import com.example.serverpost.model.Post;
import com.example.serverpost.service.CommentService;
import com.example.serverpost.service.PostService;
import com.example.serverpost.service.Url;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/post")
@Api(tags = "Контролер публікації для анонімних користувачів")
@Slf4j
public class AnonymousPostController {
    private final PostService postService;
    private final CommentService commentService;

    public AnonymousPostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/get_by_range_price")
    @ApiOperation("Всі публікації в заданому діапазоні ціни")
    public List<PostDto> getByRangePrice(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max){
        if(min == null)
            min = 0;
        if(max == null) {
            Comparator<Post> comparator = (o1, o2) -> o1.getPrice().compareTo(o2.getPrice());
            max = postService.getAllPost()
                    .stream()
                    .max(comparator)
                    .orElseThrow(() -> new PostNotFoundException("Post not found"))
                    .getPrice();
        }

        Integer finalMin = min;
        Integer finalMax = max;
        return PostDto.create(
                postService.getAllPost().stream()
                .filter(post -> post.getPrice() >= finalMin)
                .filter(post -> post.getPrice() <= finalMax)
                .collect(Collectors.toList()));
    }
    @GetMapping("/sort")
    @ApiOperation("Сортувати публікацій, param = (name, date, price)")
    public List<PostDto> sort(@RequestParam(required = false) String sortBy){
        Comparator<Post> comparator;

        switch(sortBy) {
            case "name":
                comparator = Comparator.comparing(Post::getName);
                break;
            case "date":
                comparator = Comparator.comparing(Post::getDate);
                break;
            case "price":
                comparator = Comparator.comparing(Post::getPrice);
                break;
            default:
                comparator = Comparator.comparing(Post::getPrice);
        }

        return PostDto.create(postService
                .getAllPost()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}/comment")
    @ApiOperation("Всі коментарі до публікації")
    public List<Comment> getAllComment(@PathVariable Long id) {

        if(postService.get(id) == null) {
            throw new PostNotFoundException("Post not found, id = " + id);
        }

        List<Comment> commentList = commentService.getAllComment(id);
        commentList.sort(Comparator.comparing(Comment::getDate));
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
