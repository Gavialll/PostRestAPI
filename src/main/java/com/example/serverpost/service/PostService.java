package com.example.serverpost.service;

import com.example.serverpost.exception.PostException;
import com.example.serverpost.model.Post;

import java.util.List;


public interface PostService {
    Post get(Long id) throws PostException;
    Post add(Post post);
    String delete(Long id);
    Post update(Long id, Post post);
    List<Post> getAllPost();
}
