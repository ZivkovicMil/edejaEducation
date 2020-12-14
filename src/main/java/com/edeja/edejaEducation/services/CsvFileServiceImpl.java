package com.edeja.edejaEducation.services;

import com.edeja.edejaEducation.controllers.UsersController;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class CsvFileServiceImpl implements CsvFileService {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    public void createFile(String fileName, String content) {
        String[] listContent = new String[]{content};
        File csvFile = new File(UploadServiceImpl.directory + "/" + fileName + ".csv");
        CSVWriter writer = null;
        try {
            if (csvFile.exists()) {
                writer = new CSVWriter(new FileWriter(csvFile, true));
                writer.writeNext(listContent);
                writer.flush();
            } else {
                csvFile = new File(fileName + ".csv");
                writer = new CSVWriter(new FileWriter(csvFile, true));
                if (csvFile.exists()) {
                    writer.writeNext(listContent);
                    writer.flush();
                } else {
                    writer.writeNext(listContent);
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
