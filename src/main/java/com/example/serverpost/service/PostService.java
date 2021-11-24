package com.example.serverpost.service;

import com.example.serverpost.dto.AddPostDto;
import com.example.serverpost.model.Post;

import java.util.List;


public interface PostService {
    Post get(Long id);
    Post add(AddPostDto addPostDto);
    void delete(Long id);
    Post update(Long id, Post post);
    List<Post> getAllPost();
}
