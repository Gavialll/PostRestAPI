package com.example.serverpost.security.jwt;

import com.example.serverpost.model.User;
import com.example.serverpost.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                grantedAuthority(user.getRole()),
                true);
    }

    public static List<GrantedAuthority> grantedAuthority(Role role){
       return new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(role.getName())));
    }
}
