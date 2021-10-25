package com.example.serverpost.controller;

import com.example.serverpost.dao.CategoryRepo;
import com.example.serverpost.exception.UserException;
import com.example.serverpost.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/anonymous/category")
public class CategoryController {
    @Autowired
    private CategoryRepo categoryRepo;
    @GetMapping("/{id}")
    public Category get(@PathVariable Long id) throws UserException {
        return categoryRepo.findById(id).orElseThrow(UserException::new);
    }
}
