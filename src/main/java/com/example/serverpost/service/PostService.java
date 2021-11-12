package com.example.serverpost.service;

import com.example.serverpost.model.Post;

import java.util.List;


public interface PostService {
    Post get(Long id);
    Post add(Post post);
    void delete(Long id);
    Post update(Long id, Post post);
    List<Post> getAllPost();
}
