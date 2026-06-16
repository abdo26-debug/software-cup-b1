package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Assignment;
import com.example.demo.model.Course;
import com.example.demo.model.Enrollment;
import com.example.demo.model.Submission;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.SubmissionRepository;
import com.example.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/teacher/courses")
    public Map<String, Object> getTeacherCourses(@RequestParam Long teacherId) {
        Map<String, Object> response = new HashMap<>();
        response.put("teacherId", teacherId);
        response.put("message", "Teacher courses endpoint working!");
        return response;
    }

    // CREATE COURSE (Teacher only)
    @PostMapping("/teacher/courses")
    public ResponseEntity<Map<String, Object>> createCourse(
            @RequestParam Long teacherId,
            @RequestBody Map<String, String> courseData) {

        Map<String, Object> response = new HashMap<>();

        if (!userRepository.existsById(teacherId)) {
            response.put("success", false);
            response.put("message", "Teacher not found");
            return ResponseEntity.badRequest().body(response);
        }

        Course course = new Course();
        course.setCourseName(courseData.get("courseName"));
        course.setCourseCode(courseData.get("courseCode"));
        course.setDescription(courseData.get("description"));
        course.setTeacherId(teacherId);
        course.setSemester(courseData.get("semester"));
        course.setCreatedAt(LocalDateTime.now());

        courseRepository.save(course);

        response.put("success", true);
        response.put("message", "Course created successfully");
        response.put("courseId", course.getId());

        return ResponseEntity.ok(response);
    }

    // CREATE ASSIGNMENT (Teacher only)
    @PostMapping("/teacher/assignments")
    public ResponseEntity<Map<String, Object>> createAssignment(@RequestBody Map<String, Object> assignmentData) {

        Map<String, Object> response = new HashMap<>();

        Long courseId = Long.valueOf(assignmentData.get("courseId").toString());

        if (!courseRepository.existsById(courseId)) {
            response.put("success", false);
            response.put("message", "Course not found");
            return ResponseEntity.badRequest().body(response);
        }

        Assignment assignment = new Assignment();
        assignment.setCourseId(courseId);
        assignment.setTitle(assignmentData.get("title").toString());
        assignment.setDescription(assignmentData.get("description").toString());
        assignment.setDueDate(LocalDate.parse(assignmentData.get("dueDate").toString()));
        assignment.setMaxScore(Integer.valueOf(assignmentData.get("maxScore").toString()));
        assignment.setCreatedAt(LocalDateTime.now());

        assignmentRepository.save(assignment);

        response.put("success", true);
        response.put("message", "Assignment created successfully");
        response.put("assignmentId", assignment.getId());

        return ResponseEntity.ok(response);
    }

    // GET STUDENT ASSIGNMENTS
    @GetMapping("/student/assignments")
    public ResponseEntity<List<Map<String, Object>>> getStudentAssignments(@RequestParam Long studentId) {

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<Map<String, Object>> assignmentsList = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            List<Assignment> courseAssignments = assignmentRepository.findByCourseId(enrollment.getCourseId());

            for (Assignment assignment : courseAssignments) {
                Map<String, Object> assignmentData = new HashMap<>();
                assignmentData.put("id", assignment.getId());
                assignmentData.put("title", assignment.getTitle());
                assignmentData.put("description", assignment.getDescription());
                assignmentData.put("dueDate", assignment.getDueDate());
                assignmentData.put("maxScore", assignment.getMaxScore());

                Optional<Submission> submission = submissionRepository.findByAssignmentIdAndStudentId(
                        assignment.getId(), studentId);

                if (submission.isPresent()) {
                    assignmentData.put("status", "submitted");
                    assignmentData.put("score", submission.get().getScore());
                } else {
                    assignmentData.put("status", "pending");
                    assignmentData.put("score", null);
                }

                assignmentsList.add(assignmentData);
            }
        }

        return ResponseEntity.ok(assignmentsList);
    }
}