package com.vaccination_portal_tracker.vaccination_portal_tracker.repository;  // FIXED: respository → repository

import com.vaccination_portal_tracker.vaccination_portal_tracker.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findAllByOrderByUploadDateDesc();
    Optional<FileMetadata> findByFileName(String fileName);
}