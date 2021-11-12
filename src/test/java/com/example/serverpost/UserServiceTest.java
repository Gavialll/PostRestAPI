package com.example.serverpost;

import com.example.serverpost.repository.RoleRepo;
import com.example.serverpost.model.User;
import com.example.serverpost.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepo roleRepo;

    @Test
    public void add(){
        User user = new User(
                "firstTest",
                "lastTest",
                "loginTest",
                "123",
                roleRepo.getById(1L),
                "img"
                );

        Assertions.assertEquals(userService.add(user).getLogin(), "loginTest");
        deleteUser();
        log.info("test(add): create user");
        log.error("test(add): error");
    }

    @Test
    public void update(){
        User user = userService.getByLogin("loginTest");
        Assertions.assertEquals(user.getFirstName(), "firstTest");
        user.setFirstName("firstTestUpdate");
        userService.update(user.getId(), user);
        user = userService.getByLogin("loginTest");
        Assertions.assertEquals(user.getFirstName(), "firstTestUpdate");
        System.out.println("updateUser: " + user);
        deleteUser();
    }


    public void deleteUser(){
        log.info("test(deleteUser): complete");
    }
}
