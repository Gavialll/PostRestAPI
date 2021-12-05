package com.example.serverpost.service.impl;

import com.example.serverpost.dto.RegistrationUserDto;
import com.example.serverpost.exception.user.UserNotFoundException;
import com.example.serverpost.exception.user.UserValidationException;
import com.example.serverpost.model.Post;
import com.example.serverpost.model.User;
import com.example.serverpost.repository.PostRepo;
import com.example.serverpost.repository.RoleRepo;
import com.example.serverpost.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements com.example.serverpost.service.UserService {
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, PostRepo postRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public  User add(RegistrationUserDto newUser){

       PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       User user = new User();

       if(userRepo.findByLogin(newUser.getLogin()) != null)
           throw new UserValidationException("Login is used");
       if(!newUser.getPassword().equals(newUser.getPasswordConfirmation()))
           throw new UserValidationException("Passwords do not match");

       user.setFirstName(newUser.getFirstName());
       user.setLastName(newUser.getLastName());
       user.setLogin(newUser.getLogin());
       user.setNumber(newUser.getNumber());
       user.setCity(newUser.getCity());
       user.setRole(roleRepo.getById(2L));
       user.setPostList(List.of());
       user.setPassword(passwordEncoder.encode(newUser.getPassword()));
       return userRepo.save(user);
    }

    @Override
    public User get(Long id){
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found, id = " + id));
    }

    @Override
    public User getByLogin(String login) {
        User user = userRepo.findByLogin(login);

        if(user == null) throw new UserNotFoundException("User not found, login = " + login);
        else return user;
    }

    @Override
    public User update(Long id, User user){
        userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found, id = " + id));
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
