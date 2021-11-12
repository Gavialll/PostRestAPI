package com.example.serverpost.service;

import com.example.serverpost.model.Post;
import com.example.serverpost.model.User;

import java.util.List;

public interface UserService {

    User add(User user);

    User get(Long id);

    User getByLogin(String login);

    User update(Long id, User user);

    void delete(Long id);

    List<Post> getAllPost(Long id);

}
