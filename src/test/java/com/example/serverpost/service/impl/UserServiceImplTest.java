package com.example.serverpost.service.impl;

import com.example.serverpost.dto.RegistrationUserDto;
import com.example.serverpost.exception.user.UserNotFoundException;
import com.example.serverpost.exception.user.UserValidationException;
import com.example.serverpost.model.Role;
import com.example.serverpost.model.User;
import com.example.serverpost.repository.RoleRepo;
import com.example.serverpost.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private RoleRepo roleRepo;

    @Mock
    private User user;

    private RegistrationUserDto registrationUserDto;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    public void before(){
        Mockito.when(user.getFirstName()).thenReturn("first name");
        Mockito.when(userRepo.save(any(User.class))).thenReturn(user);
        Mockito.when(userRepo.findByLogin(anyString())).thenReturn(null);
        Mockito.when(roleRepo.getById(2L)).thenReturn(new Role());


        registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setFirstName("first name");
        registrationUserDto.setLogin("login@email.com");
        registrationUserDto.setNumber("+380678755165");
        registrationUserDto.setPassword("123");
        registrationUserDto.setPasswordConfirmation("123");
    }

    @Test
    void add() {
        User user = userService.add(registrationUserDto);
        assertEquals("first name", user.getFirstName());
    }

    @Test
    void add_Throw_LoginIsUsed() {
        registrationUserDto.setLogin("test@email.com");
        Mockito.when(userRepo.findByLogin("test@email.com")).thenReturn(user);
        String loginIsUsed = assertThrows(UserValidationException.class, () -> userService.add(registrationUserDto)).getMessage();
        assertEquals(loginIsUsed, "Login is used");
    }

    @Test
    void add_Throw_PasswordsDoNotMatch() {
        registrationUserDto.setPassword("123");
        registrationUserDto.setPasswordConfirmation("456");
        String passwordsDoNotMatch = assertThrows(UserValidationException.class,
                () -> userService.add(registrationUserDto)).getMessage();
        assertEquals(passwordsDoNotMatch, "Passwords do not match");
    }

    @Test
    void get() {
        Mockito.when(userRepo.findById(1000L)).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.get(1000L).getFirstName(), "first name");

        Mockito.when(userRepo.findById(999L)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, ()-> userService.get(999L));
    }

    @Test
    void getByLogin() {
        Mockito.when(userRepo.findByLogin("login@email.com")).thenReturn(user);
        assertEquals(userService.getByLogin("login@email.com").getFirstName(), "first name");

        Mockito.when(userRepo.findByLogin("throw@email.com")).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, ()-> userService.getByLogin("throw@email.com"));
    }
}