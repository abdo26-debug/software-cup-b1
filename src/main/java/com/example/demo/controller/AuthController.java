package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    // ==================== LOGIN API ====================
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        
        // Check if user exists
        if (userOptional.isEmpty()) {
            return new LoginResponse(false, "User not found");
        }
        
        User user = userOptional.get();
        
        // Check password
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return new LoginResponse(false, "Invalid password");
        }
        
        // Login successful
        return new LoginResponse(true, "Login successful", 
            user.getRole(), user.getName(), user.getId());
    }
    
    // ==================== REGISTRATION API ====================
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole().toLowerCase());
        user.setStudentId(request.getStudentId());
        user.setCreatedAt(LocalDateTime.now());
        
        // Save to database
        userRepository.save(user);
        
        // Return success response
        response.put("success", true);
        response.put("message", "Registration successful");
        return ResponseEntity.ok(response);
    }
}