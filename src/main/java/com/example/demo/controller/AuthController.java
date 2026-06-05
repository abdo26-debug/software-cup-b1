package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        
        if (userOptional.isEmpty()) {
            return new LoginResponse(false, "User not found");
        }
        
        User user = userOptional.get();
        
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return new LoginResponse(false, "Invalid password");
        }
        
        return new LoginResponse(true, "Login successful", 
            user.getRole(), user.getName(), user.getId());
    }
}