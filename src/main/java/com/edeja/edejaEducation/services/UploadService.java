package com.edeja.edejaEducation.services;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    void upload(MultipartFile file);
}
