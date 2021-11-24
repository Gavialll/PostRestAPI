package com.example.serverpost.service.impl;

import com.example.serverpost.component.AuthenticationUser;
import com.example.serverpost.dto.AddPostDto;
import com.example.serverpost.exception.post.PostNotFoundException;
import com.example.serverpost.exception.post.PostPriceException;
import com.example.serverpost.model.Post;
import com.example.serverpost.repository.PostRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements com.example.serverpost.service.PostService {
    private final PostRepo postRepo;
    private final AuthenticationUser authentication;

    public PostServiceImpl(PostRepo postRepo, AuthenticationUser authentication) {
        this.postRepo = postRepo;
        this.authentication = authentication;
    }

    @Override
    public Post get(Long id){
        return postRepo.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found, id = " + id));
    }

    @Override
    public Post add(AddPostDto post) {
        // TODO: 12.11.2021 Зробити валідацію полів при додаванні публікації.
        if(post.getPrice() == null || post.getPrice() < 0)
            throw new PostPriceException("Price no validation");
        if(post.getCategory() == null) {
            post.setCategory(0L);
        }
        return postRepo.save(create(post));
    }

    @Override
    public void delete(Long id) {
        postRepo.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found, id = " + id));
        postRepo.deleteById(id);
    }

    @Override
    public Post update(Long id, Post post) {
        post.setDate(LocalDateTime.now());
        return postRepo.save(post);
    }

    @Override
    public List<Post> getAllPost(){
        return postRepo.findAll();
    }

    public Post create(AddPostDto addPost){
        Post post = new Post();
            post.setCategory(addPost.getCategory());
            post.setName(addPost.getName());
            post.setPrice(addPost.getPrice());
            post.setDescription(addPost.getDescription());
            post.setDate(LocalDateTime.now());
            post.setUserId(authentication.Id());
        return post;
    }
}
