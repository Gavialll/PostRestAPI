package com.example.serverpost.service;

import com.example.serverpost.exception.UserException;
import com.example.serverpost.model.Post;
import com.example.serverpost.model.User;

import java.util.List;

public interface UserService {

    public User add(User user);

    public User get(Long id) throws UserException;

    public User getByLogin(String login);

    public User update(Long id, User user);

    public String delete(Long id);

    public List<Post> getAllPost(Long id);




}
