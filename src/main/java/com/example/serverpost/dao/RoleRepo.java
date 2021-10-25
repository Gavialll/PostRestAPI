package com.example.serverpost.dao;

import com.example.serverpost.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
