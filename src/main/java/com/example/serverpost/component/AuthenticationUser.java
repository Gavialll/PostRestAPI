package com.example.serverpost.component;

import com.example.serverpost.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUser {
    private final UserService userService;

    public AuthenticationUser(UserService userService) {
        this.userService = userService;
    }

    public Long Id(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByLogin(authentication.getName()).getId();
    }
}
