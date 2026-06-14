package com.example.demo.dto;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    private String studentId;
    
    // Constructors
    public RegisterRequest() {}
    
    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getStudentId() { return studentId; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
}