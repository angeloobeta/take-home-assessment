package org.takehomeassessment.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.takehomeassessment.data.dtos.response.FileResponseDto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import org.takehomeassessment.data.entities.File;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class FileUtils {
    public FileResponseDto mapVideoToDto(File file){
        return FileResponseDto.builder()
                .timeStamp(file.getTimeStamp())
                .fileUrl(file.getFileUrl())
                .fileId(String.valueOf(file.getFileId()))
                .fileName(file.getFileName())
                .fileSize(file.getFileSize())
                .build();
    }



    public static String saveFileToDisk(InputStream fileInputStream, String filename) throws IOException {
        // Define the absolute path to the root directory of the server
        String filesFolder = "UploadedFilesDirectory"; // Replace with the actual path

        // Get the absolute path to the videos folder
        Path filesFolderPath = Paths.get(filesFolder).toAbsolutePath();

        if (!Files.exists(filesFolderPath)) {
            try {
                Files.createDirectories(filesFolderPath);
            } catch (IOException e) {
                // Handle the exception if directory creation fails
                e.printStackTrace();
                System.out.println("Failed to create the directory: "+ filesFolder);
                throw e;
            }
        }

        // Generate a unique identifier for the file
        String uniqueIdentifier = UUID.randomUUID().toString();
        String filePath = Paths.get(filesFolderPath.toString(), LocalDateTime.now() + uniqueIdentifier + "_" + filename).toString();

        // Save the uploaded file to the "UploadedFileDirectory" folder.
        try (OutputStream fileOutputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }

        System.out.println("File uploaded successfully");
//            System.out.println(beanConfig.apiKey);
        return filePath;
    }

}





