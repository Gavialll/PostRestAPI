package com.example.serverpost.controller;

import com.example.serverpost.model.User;
import com.example.serverpost.repository.UserRepo;
import com.example.serverpost.service.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(description = "Контролер реєстрації")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    //неготово
    //винести окремий контроллер реєстрація і логін
    @PostMapping("/registration")
    @ApiOperation("Реєстрація корстувача")
    public Long addUser(@RequestBody User user){
        userService.add(user);
        return user.getId();
    }
}
