package com.example.serverpost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class AuthenticationRequestDto {
    private String login;
    private String password;
}
