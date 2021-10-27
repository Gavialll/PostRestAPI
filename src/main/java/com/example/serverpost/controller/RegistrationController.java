package com.example.serverpost.controller;

import com.example.serverpost.model.User;
import com.example.serverpost.repository.UserRepo;
import com.example.serverpost.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RegistrationController {
    @Autowired
    private UserService userService;

    //неготово
    //винести окремий контроллер реєстрація і логін
    @PostMapping("/registration")
    public Long addUser(@RequestBody User user){
        userService.add(user);
        return user.getId();
    }
}
