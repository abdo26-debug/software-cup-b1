package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {
    
    @GetMapping("/test")
    public Map<String, String> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "Dashboard controller is working!");
        return response;
    }
    
    @GetMapping("/student/courses")
    public Map<String, Object> getStudentCourses(@RequestParam Long studentId) {
        Map<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("message", "Student courses endpoint working!");
        return response;
    }
    
    // ✅ ADD THIS NEW METHOD HERE ↓↓↓
    @GetMapping("/teacher/courses")
    public Map<String, Object> getTeacherCourses(@RequestParam Long teacherId) {
        Map<String, Object> response = new HashMap<>();
        response.put("teacherId", teacherId);
        response.put("message", "Teacher courses endpoint working!");
        return response;
    }
    // ✅ ADD UP TO HERE ↑↑↑
}