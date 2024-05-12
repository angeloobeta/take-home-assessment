package org.takehomeassessment.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.takehomeassessment.data.entities.File;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

    // Custom query to find videos by filename
    List<File> findByFileName(String fileName);
    List<File> findByFileSize(String fileSize);
    // Custom query to find videos uploaded after a certain timestamp
    Optional<File> findByFileId(String id);

}

