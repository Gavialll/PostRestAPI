package com.example.serverpost.repository;

import com.example.serverpost.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findAll();
    User findByLogin(String userName);
}
