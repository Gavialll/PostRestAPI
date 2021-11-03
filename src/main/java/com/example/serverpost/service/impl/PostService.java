package com.example.serverpost.service.impl;

import com.example.serverpost.repository.PostRepo;
import com.example.serverpost.exception.PostException;
import com.example.serverpost.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@Service
public class PostService implements com.example.serverpost.service.PostService {
    private final PostRepo postRepo;

    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public Post get(Long id) throws PostException {
        return postRepo.findById(id).orElseThrow(() -> new PostException(id));
    }

    @Override
    public Post add(Post post) {
        post.setDate(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public String delete(Long id) {
        postRepo.deleteById(id);
        return "Delete Post Complete";
    }

    @Override
    public Post update(Long id, Post post) {
        post.setDate(LocalDateTime.now());
        post.setId(id);
        return postRepo.save(post);
    }

    @Override
    public List<Post> getAllPost(){
        return postRepo.findAll();
    }
}
