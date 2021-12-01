package com.example.serverpost.service;

import com.example.serverpost.model.User;
import com.example.serverpost.repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setLogin("login test");
        userRepo.save(user).getId();
    }

    @AfterEach
    void tearDown() {
        userRepo.deleteById(userRepo.findByLogin("login test").getId());
    }

    @Test
    void add() {
        assertEquals("login test", userRepo.findByLogin("login test").getLogin());
    }

    @Test
    void get() {
    }

    @Test
    void getByLogin() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllPost() {
    }
}