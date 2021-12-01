package com.example.serverpost.security;


import com.example.serverpost.dto.AuthenticationRequestDto;
import com.example.serverpost.model.User;
import com.example.serverpost.repository.UserRepo;
import com.example.serverpost.security.jwt.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Api(tags = "Контролер для автоизації")
public class SecurityController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepo userService;

    @Autowired
    public SecurityController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepo userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation("Авторизація користувача")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String login = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
            User user = userService.findByLogin(login);

            if (user == null) {
                throw new UsernameNotFoundException("User with login: " + login + " not found");
            }

            String token = jwtTokenProvider.createToken(login, List.of(user.getRole()));

            Map<Object, Object> response = new HashMap<>();
            response.put("login", login);
            response.put("token", "Bearer_" + token);


            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
