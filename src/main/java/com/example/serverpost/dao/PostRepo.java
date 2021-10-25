package com.example.serverpost.dao;

import com.example.serverpost.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long id);
}
