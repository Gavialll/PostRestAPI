package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.component.FileService;
import com.example.serverpost.dto.UpdateUserDto;
import com.example.serverpost.dto.UserDto;
import com.example.serverpost.exception.user.UserImageException;
import com.example.serverpost.model.User;
import com.example.serverpost.service.Url;
import com.example.serverpost.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
@Api(tags = "Контролер для управління акаунтом")
public class AccountUserController {

    private final UserService userService;
    private final AuthenticationUser authentication;

    public AccountUserController(UserService userService, AuthenticationUser authentication) {
        this.userService = userService;
        this.authentication = authentication;
    }

    @GetMapping("/id")
    @ApiOperation("Ідентифакатор акаунта")
    public Long getId(){
        return authentication.Id();
    }

    @GetMapping()
    @ApiOperation("Інформація про користувача, і його публікації")
    public UserDto getUser(){
        return UserDto.create(userService.get(authentication.Id()));
    }

    @PostMapping("/img")
    @ApiOperation("Додати фото користувача")
    public HttpStatus addImg(@RequestParam MultipartFile file){

        Path path = Paths.get(Url.user);
        User user = userService.get(authentication.Id());

        if(user.getImg() != null){
            File photo = new File(Url.user + user.getImg());
            if(!photo.delete()) {
                throw new UserImageException("The previous image was not deleted");
            }
        }

        user.setImg(FileService.save(file, path));
        userService.update(authentication.Id(), user);
        return HttpStatus.OK;
    }

    @PutMapping()
    @ApiOperation("Редагування інфомації користувача")
    public UserDto updateUser(@RequestBody UpdateUserDto updateUserDto){

        User user = userService.get(authentication.Id());

        if(updateUserDto.getFirstName() != null) user.setFirstName(updateUserDto.getFirstName());
        if(updateUserDto.getLastName() != null) user.setLastName(updateUserDto.getLastName());
        if(updateUserDto.getCity() != null) user.setCity(updateUserDto.getCity());
        if(updateUserDto.getNumber() != null) user.setNumber(updateUserDto.getNumber());

        return UserDto.create(userService.update(authentication.Id(), user));
    }

    @DeleteMapping()
    @ApiOperation("Видалити акаунт")
    public HttpStatus deleteUser(){
        userService.delete(authentication.Id());
        return HttpStatus.OK;
    }
}