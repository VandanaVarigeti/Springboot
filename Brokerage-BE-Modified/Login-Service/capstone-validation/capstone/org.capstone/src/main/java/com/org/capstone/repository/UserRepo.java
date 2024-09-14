package com.org.capstone.repository;

import com.org.capstone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {

    public User findByUsername(String username);
}
