package com.org.capstone.controller;

import com.org.capstone.model.ChangePasswordRequest;
import com.org.capstone.model.User;
import com.org.capstone.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*")
public class Home {




    @RequestMapping("/welcome")
    public String welcome() {
        String text = "this is private page ";
        text += "this page is not allowed to unauthenticated users";
        return text;
    }


    @RequestMapping("/check")
    public String getUser() {
        return "User has Logged in ";
    }


}