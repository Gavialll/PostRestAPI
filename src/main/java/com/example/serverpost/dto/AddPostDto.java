package com.example.serverpost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPostDto {
    private String name;
    private String description;
    private Integer price;
    private Long category;

    public AddPostDto() {

    }
}
