package com.example.serverpost.controller;

import com.example.serverpost.component.FileService;
import com.example.serverpost.dto.UserDto;
import com.example.serverpost.service.Url;
import com.example.serverpost.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/user")
@Api(tags = "Контролер користувачів для анонімних користувачів")
public class AnonymousUserController {
    private final UserService userService;

    public AnonymousUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Дістати користувач по ID, і всі його публікації")
    public UserDto getUser(@PathVariable long id){
        return UserDto.create(userService.get(id));
    }

    @GetMapping("/{id}/img")
    @ApiOperation("Фото користувача")
    public ResponseEntity getImg(@PathVariable Long id) throws IOException {
        BufferedImage bufferedImage = ImageIO
                .read(FileService.getFile(userService.get(id).getImg(), Url.user));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }
}
