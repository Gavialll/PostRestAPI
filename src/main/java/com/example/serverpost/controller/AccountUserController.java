package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.dto.UserDto;
import com.example.serverpost.exception.UserException;
import com.example.serverpost.model.User;
import com.example.serverpost.service.FileService;
import com.example.serverpost.service.Url;
import com.example.serverpost.service.UserService;
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

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationUser authentication;


    @GetMapping("/id")
    public Long getId(){
        return authentication.Id();
    }

    @GetMapping()
    public UserDto getUser() throws UserException {
        return UserDto.create(userService.get(authentication.Id()));
    }

    @PutMapping()
    public UserDto updateUser(@RequestBody UserDto userDto) throws UserException {
        User user = userService.get(authentication.Id());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCity(userDto.getCity());
        user.setNumber(userDto.getNumber());

        userService.update(authentication.Id(), user);
        return UserDto.create(userService.get(authentication.Id()));
    }

    @DeleteMapping()
    public Boolean deleteUser(){
        userService.delete(authentication.Id());
        return true;
    }

    @PostMapping("/img")
    public Boolean addImg(@RequestParam MultipartFile file) throws UserException {
        Path path = Paths.get(Url.user);
        User user = userService.get(authentication.Id());

        // при зміні фото профіля видаляєм старе фото
        if(user.getImg() != null){
            File photo = new File(Url.user + user.getImg());
            if(photo.delete()) System.out.println("true photo delete");
            else System.out.println("false photo delete");
        }

        //Зберігаєм назву фото до БД
        user.setImg(FileService.save(file, path));
        userService.update(authentication.Id(), user);
        return true;
    }
}