package com.vaccination_portal_tracker.vaccination_portal_tracker.controller;

import com.vaccination_portal_tracker.vaccination_portal_tracker.model.FileMetadata;
import com.vaccination_portal_tracker.vaccination_portal_tracker.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            if (!isValidFileType(file.getContentType())) {
                return ResponseEntity.badRequest().body("Only PDF and image files are allowed");
            }

            fileStorageService.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping
    public List<FileMetadata> getAllFiles() {
        return fileStorageService.getAllFiles();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        try {
            fileStorageService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete file: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileMetadata> getFileById(@PathVariable Long id) {
        try {
            FileMetadata file = fileStorageService.getFileById(id);
            return ResponseEntity.ok(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean isValidFileType(String contentType) {
        return contentType != null &&
                (contentType.equals("application/pdf") ||
                        contentType.startsWith("image/"));
    }
}