package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.controllers.UsersController;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadServiceImpl implements UploadService {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    public static String directory = "C:\\Projekti\\playGround" + "/uploads";

    public void upload(MultipartFile file) {
        if (Files.getFileExtension(file.getOriginalFilename()).equals("csv")) {
            new File(directory).mkdir();
            Path path = Paths.get(directory, file.getOriginalFilename());
            log.info("User has uploaded .csv file");
            try {
                java.nio.file.Files.write(path, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info("The file does not have right extension!");
        }
    }
}
