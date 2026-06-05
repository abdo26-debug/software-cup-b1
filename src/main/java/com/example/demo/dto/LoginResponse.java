package com.example.demo.dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private String role;
    private String name;
    private Long userId;
    
    // Constructor 1: For error responses
    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    // Constructor 2: For successful login
    public LoginResponse(boolean success, String message, String role, String name, Long userId) {
        this.success = success;
        this.message = message;
        this.role = role;
        this.name = name;
        this.userId = userId;
    }
    
    // Getters
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getName() {
        return name;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}