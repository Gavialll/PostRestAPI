package com.example.serverpost.dto;

import com.example.serverpost.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private List<PostDto> postList;


    public static CategoryDto create(Category category){
        return new
                CategoryDto(category.getId(),
                category.getName(),
                PostDto.create(category.getPostList()));
    }
}
