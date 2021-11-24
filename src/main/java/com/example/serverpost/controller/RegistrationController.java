package com.example.serverpost.controller;

import com.example.serverpost.dto.RegistrationUserDto;
import com.example.serverpost.dto.UserDto;
import com.example.serverpost.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(tags = "Контролер реєстрації")
public class RegistrationController {
    private final UserServiceImpl userService;

    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    @ApiOperation("Реєстрація корстувача")
    public UserDto registrationUser(@RequestBody RegistrationUserDto newUser) {
            return UserDto.create(userService.add(newUser));
    }
}
