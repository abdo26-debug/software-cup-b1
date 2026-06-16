package com.example.demo.repository;

import com.example.demo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacherId(Long teacherId);
    
    @Query("SELECT c FROM Course c JOIN Enrollment e ON c.id = e.courseId WHERE e.studentId = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);
}