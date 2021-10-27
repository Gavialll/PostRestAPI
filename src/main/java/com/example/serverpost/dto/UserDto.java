package com.example.serverpost.dto;

import com.example.serverpost.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String city;
    private String number;
    private List<PostDto> postList;

    public static UserDto create(User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getCity(),
                user.getNumber(),
                PostDto.create(user.getPostList()));
    }
}
