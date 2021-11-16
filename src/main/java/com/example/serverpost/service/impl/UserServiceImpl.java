package com.example.serverpost.service.impl;

import com.example.serverpost.exception.user.UserNotFoundException;
import com.example.serverpost.model.Post;
import com.example.serverpost.model.User;
import com.example.serverpost.repository.PostRepo;
import com.example.serverpost.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements com.example.serverpost.service.UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;

    @Override
    public  User add(User user){
       return userRepo.save(user);
    }

    @Override
    public User get(Long id){
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found, id = " + id));
    }

    @Override
    public User getByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public User update(Long id, User user){
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id){
        userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found, id = " + id));
        userRepo.deleteById(id);
    }

    @Override
    public List<Post> getAllPost(Long id){
        return postRepo.findAllByUserId(id);
    }
}
