package com.example.serverpost.controller;

import com.example.serverpost.dto.RegistrationUserDto;
import com.example.serverpost.dto.UserDto;
import com.example.serverpost.exception.LoginDuplicateException;
import com.example.serverpost.model.User;
import com.example.serverpost.repository.RoleRepo;
import com.example.serverpost.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Api(description = "Контролер реєстрації")
public class RegistrationController {
    private final UserServiceImpl userService;
    private final RoleRepo roleRepo;

    public RegistrationController(UserServiceImpl userService, RoleRepo roleRepo) {
        this.userService = userService;
        this.roleRepo = roleRepo;
    }

    @PostMapping("/registration")
    @ApiOperation("Реєстрація корстувача")
    public UserDto registrationUser(@RequestBody RegistrationUserDto newUser) {
        if(userService.getByLogin(newUser.getLogin()) != null) throw new LoginDuplicateException("Login Duplicate");
            User user = new User();
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setLogin(newUser.getLogin());
            user.setNumber(newUser.getNumber());
            user.setCity(newUser.getCity());
            user.setRole(roleRepo.getById(2L));
            user.setPostList(List.of());
            return UserDto.create(userService.add(user));

    }
}
