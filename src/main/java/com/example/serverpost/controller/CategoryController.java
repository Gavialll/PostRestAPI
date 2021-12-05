package com.example.serverpost.controller;

import com.example.serverpost.dto.CategoryDto;
import com.example.serverpost.exception.category.CategoryException;
import com.example.serverpost.repository.CategoryRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/category")
@Slf4j
@Api(tags = "Контролер категорій")
public class CategoryController {
    private final CategoryRepo categoryRepo;

    public CategoryController(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/{id}")
    @ApiOperation("Дістаєм категорію, і всі її публікаціїї")
    public CategoryDto get(@PathVariable Long id) {

        return CategoryDto.create(
                categoryRepo.findById(id)
                .orElseThrow(() -> {
                    throw new CategoryException("Category not found, id = " + id);
                }));
    }
}
