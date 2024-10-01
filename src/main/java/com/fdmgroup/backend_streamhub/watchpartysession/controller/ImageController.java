package com.fdmgroup.backend_streamhub.watchpartysession.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    // Save image in a local directory
    @PostMapping("/upload")
    public String saveImageToStorage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("fileName") String fileName,
            @RequestParam("directory") String directory) {
        try {
            String path = "src/main/resources/" + directory;
            Path uploadPath = Path.of(path);
            Path filePath = uploadPath.resolve(fileName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
