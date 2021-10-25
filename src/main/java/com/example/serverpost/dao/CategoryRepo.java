package com.example.serverpost.dao;

import com.example.serverpost.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
