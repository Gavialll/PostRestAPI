package com.example.serverpost.controller;

import com.example.serverpost.exception.category.CategoryException;
import com.example.serverpost.repository.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CategoryControllerTest {

    @MockBean
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryController categoryController;

    @Test
    void get() {
        Mockito.when(categoryRepo.findById(100L)).thenThrow(CategoryException.class);

        assertThrows(CategoryException.class, () -> categoryController.get(100L));
    }
}