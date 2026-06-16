package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "course_name")
    private String courseName;
    
    @Column(name = "course_code")
    private String courseCode;
    
    private String description;
    
    @Column(name = "teacher_id")
    private Long teacherId;
    
    private String semester;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public Course() {}
    
    // Getters
    public Long getId() { return id; }
    public String getCourseName() { return courseName; }
    public String getCourseCode() { return courseCode; }
    public String getDescription() { return description; }
    public Long getTeacherId() { return teacherId; }
    public String getSemester() { return semester; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public void setDescription(String description) { this.description = description; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}