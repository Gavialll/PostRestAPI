package com.example.serverpost.service.impl;
import com.example.serverpost.repository.PostRepo;
import com.example.serverpost.repository.UserRepo;
import com.example.serverpost.exception.UserException;
import com.example.serverpost.model.Post;
import com.example.serverpost.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements com.example.serverpost.service.UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;

    @Override
    public  User add(User user){
       return userRepo.save(user);
    }

    @Override
    public User get(Long id) throws UserException {
        return userRepo.findById(id).orElseThrow(UserException::new);
    }

    @Override
    public User getByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public User update(Long id, User user){
        User userUpdate = userRepo.getById(id);
        userUpdate.setFirstName(user.getFirstName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setLogin(user.getLogin());
        userUpdate.setCity(user.getCity());
        userUpdate.setNumber(user.getNumber());
        return userRepo.save(userUpdate);
    }
    public String delete(Long id){
        userRepo.deleteById(id);
        return "Delete User Complete";
    }

    @Override
    public List<Post> getAllPost(Long id){
        return postRepo.findAllByUserId(id);
    }
}
