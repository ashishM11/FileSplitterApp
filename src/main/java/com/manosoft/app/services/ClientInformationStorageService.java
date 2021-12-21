package com.manosoft.app.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.manosoft.app.entities.ClientInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

@Service
public class ClientInformationStorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public Boolean uploadClientInfo(ClientInformation clientInformation) {
        if (clientInformation.getFileName().split("\\.")[1].equals("csv")) {
            if (!s3Client.doesObjectExist(bucketName, clientInformation.getClientID() + "/source/" + clientInformation.getFileName())) {
                s3Client.putObject(bucketName, clientInformation.getClientID() + "/source/" + clientInformation.getFileName(), clientInformation.getFilePath());
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public List<String> getAllFilesFromBucket() {
        return null;
    }

    public byte[] downloadFile(String fileName) {
        return null;
    }

    public String deleteFile(String fileName) {
        return null;
    }

    public List<String> performSplitting(String clientID, String fileName) throws FileNotFoundException {
        if (s3Client.doesObjectExist(bucketName, clientID + "/source/" + fileName)) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", "aws ls help 2>&1; true");
            // processBuilder.command("bash", "-c", "usr/bin/aws ls help 2>&1; true"); this we can use in unix env
            try {
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                int exitCode = process.waitFor();
                System.out.println("\nExited with error code : " + exitCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
