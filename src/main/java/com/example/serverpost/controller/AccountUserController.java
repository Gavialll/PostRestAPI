package com.example.serverpost.controller;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.component.FileService;
import com.example.serverpost.dto.UpdateUserDto;
import com.example.serverpost.dto.UserDto;
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
@Api(description = "Контролер для управління акаунтом")
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

        // при зміні фото профіля видаляєм старе фото
        if(user.getImg() != null){
            File photo = new File(Url.user + user.getImg());
            // TODO: 12.11.2021 exception якщо фото невидалилося
            if(photo.delete()) System.out.println("true photo delete");
            else System.out.println("false photo delete");
        }

        //Зберігаєм назву фото до БД
        user.setImg(FileService.save(file, path));
        userService.update(authentication.Id(), user);
        return HttpStatus.OK;
    }

    @PutMapping()
    @ApiOperation("Редагування інфомації користувача")
    public UserDto updateUser(@RequestBody UpdateUserDto updateUserDto){
        User user = userService.get(authentication.Id());
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setCity(updateUserDto.getCity());
        user.setNumber(updateUserDto.getNumber());

        return UserDto.create(userService.update(authentication.Id(), user));
    }

    @DeleteMapping()
    @ApiOperation("Видалити акаунт")
    public HttpStatus deleteUser(){
        userService.delete(authentication.Id());
        return HttpStatus.OK;
    }
}