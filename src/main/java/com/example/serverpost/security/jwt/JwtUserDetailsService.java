package com.example.serverpost.security.jwt;

import com.example.serverpost.repository.UserRepo;
import com.example.serverpost.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return JwtUserFactory.create(user);
    }
}
