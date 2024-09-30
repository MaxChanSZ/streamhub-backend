package com.fdmgroup.backend_streamhub.watchpartysession.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadImageRequest {
    private MultipartFile image;
    private String fileName;
    private String directory;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
