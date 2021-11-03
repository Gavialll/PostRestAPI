package com.example.serverpost.controller;

import com.example.serverpost.dto.CategoryDto;
import com.example.serverpost.repository.CategoryRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/category")
@Api(description = "Контролер категорій")
public class CategoryController {
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/{id}")
    @ApiOperation("Дістаєм категорію, і всі її публікаціїї")
    public CategoryDto get(@PathVariable Long id) throws Exception {
        return CategoryDto.create(categoryRepo.findById(id).orElseThrow(Exception::new));
    }
}
