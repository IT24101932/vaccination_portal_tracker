package com.vaccination_portal_tracker.vaccination_portal_tracker.service;

import com.vaccination_portal_tracker.vaccination_portal_tracker.model.FileMetadata;
import com.vaccination_portal_tracker.vaccination_portal_tracker.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    public FileMetadata storeFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        Files.copy(file.getInputStream(), filePath);

        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName(fileName);
        fileMetadata.setOriginalFileName(file.getOriginalFilename());
        fileMetadata.setFileType(file.getContentType());
        fileMetadata.setFileSize(file.getSize());
        fileMetadata.setUploadDate(LocalDateTime.now());
        fileMetadata.setFilePath(filePath.toString());

        return fileRepository.save(fileMetadata);
    }

    public List<FileMetadata> getAllFiles() {
        return fileRepository.findAllByOrderByUploadDateDesc();
    }

    public void deleteFile(Long fileId) throws IOException {
        FileMetadata fileMetadata = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + fileId));

        Path filePath = Paths.get(fileMetadata.getFilePath());
        Files.deleteIfExists(filePath);

        fileRepository.deleteById(fileId);
    }

    public FileMetadata getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + id));
    }
}