package com.org.capstone.service;

import com.org.capstone.Feign.UserClient;
import com.org.capstone.model.CustomUserDetails;
import com.org.capstone.model.Registration;
import com.org.capstone.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      //User user = this.userRepo.findByUsername(username);

     Registration registration = (Registration) userClient.getUser(username);


       if(registration ==  null){
           throw  new UsernameNotFoundException("User not found");
       }

       else{

           String password=registration.getPassword();

           return new User(username,password,new ArrayList<>());
       }
        //        if(username.equals("Durgesh")){
//            return new User (  "Durgesh", "Durgesh123", new ArrayList<>());
//        }else {
//            throw new UsernameNotFoundException("User not found !!");
//        }
//        }
        
    }
}