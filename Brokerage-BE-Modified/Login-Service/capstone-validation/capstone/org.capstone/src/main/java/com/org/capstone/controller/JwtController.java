package com.org.capstone.controller;

import com.org.capstone.Feign.UserClient;
import com.org.capstone.helper.JwtUtil;
import com.org.capstone.model.JwtRequest;
import com.org.capstone.model.JwtResponse;
import com.org.capstone.service.CustomUserDetailsService;

import com.org.capstone.model.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
//@CrossOrigin(origins = "*")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserClient userClient;

    public JwtController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil, UserClient userClient) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userClient = userClient;
    }

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {


        Registration user = (Registration) userClient.getUser(jwtRequest.getUsername());
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (UsernameNotFoundException e) {
            throw new Exception("Bad Credentials: Username not found", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials: Invalid password", e);
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        System.out.println("JWT: " + token);

        // Return the JWT token in the response
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String,String> payload){
        String username= payload.get("username");
        userClient.updatePassword(username,payload);
        return ResponseEntity.ok("Password updated successfully.");
    }

    private void authenticate(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTAILS ",e);
        }
    }

}
