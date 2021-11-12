package com.example.serverpost.dto;

import com.example.serverpost.model.Post;
import com.example.serverpost.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String city;
    private String number;
}
