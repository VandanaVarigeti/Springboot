package com.org.capstone.Feign;

import com.org.capstone.model.Registration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "REGISTRATION-SERVICE")
public interface UserClient {

    @GetMapping("/register/user/{userName}")
    Registration getUser(@PathVariable("userName")String userName);

    @PutMapping("/register/password/{userName}")
    Registration updatePassword(@PathVariable("userName")String userName, @RequestBody Map<String,String> payload);
}
