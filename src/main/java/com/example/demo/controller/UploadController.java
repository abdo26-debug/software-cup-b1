package com.example.demo.controller;

import com.example.demo.model.Submission;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class UploadController {
    
    @Autowired
    private SubmissionRepository submissionRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    private final String UPLOAD_DIR = "uploads/";
    
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitAssignment(
            @RequestParam Long assignmentId,
            @RequestParam Long studentId,
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Check if assignment exists
        if (!assignmentRepository.existsById(assignmentId)) {
            response.put("success", false);
            response.put("message", "Assignment not found");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Check if already submitted
        if (submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId).isPresent()) {
            response.put("success", false);
            response.put("message", "You have already submitted this assignment");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            // Create upload directory
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Save file with unique name
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath);
            
            // Save submission record
            Submission submission = new Submission();
            submission.setAssignmentId(assignmentId);
            submission.setStudentId(studentId);
            submission.setFilePath(filePath.toString());
            submission.setSubmittedAt(LocalDateTime.now());
            submission.setStatus("submitted");
            
            submissionRepository.save(submission);
            
            response.put("success", true);
            response.put("message", "Assignment submitted successfully");
            response.put("submissionId", submission.getId());
            
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
        
        return ResponseEntity.ok(response);
    }
}