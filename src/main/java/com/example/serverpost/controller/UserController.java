package com.example.serverpost.controller;

import com.example.serverpost.exception.UserException;
import com.example.serverpost.model.User;
import com.example.serverpost.service.FileService;
import com.example.serverpost.service.Url;
import com.example.serverpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {
    @Autowired
    private UserService userService;

    //окремий контроллер реєстрація і логін
    @PostMapping("/registration")
    public Long addUser(@RequestBody User user){
        userService.add(user);
        return user.getId();
    }

    @GetMapping("/{id}/img")
    public ResponseEntity getImg(@PathVariable Long id) throws IOException, UserException {
        BufferedImage bufferedImage = ImageIO
                .read(FileService.getFile(userService.get(id).getImg(), Url.user));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(baos.toByteArray());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) throws UserException{
        return userService.get(id);
    }
}
