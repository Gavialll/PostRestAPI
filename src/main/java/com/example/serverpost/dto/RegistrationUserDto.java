package com.example.serverpost.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegistrationUserDto {
    private String city;
    private String firstName;
    private String lastName;
    private String login;
    private String number;
    private String password;
    private String passwordConfirmation;
}
