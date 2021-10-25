package com.example.serverpost.component;

import com.example.serverpost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUser {
    public static Long Id(UserService userService){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByLogin(authentication.getName()).getId();
    }

    public static Long getIdAccount(){
        return Id(new com.example.serverpost.service.impl.UserService());
    }
}
