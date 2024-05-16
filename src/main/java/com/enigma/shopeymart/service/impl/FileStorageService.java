package com.enigma.shopeymart.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("/home/user/batch#14/shopeymart/src/main/java/com/enigma/shopeymart/upload-download_java");

    public FileStorageService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Could not create directory where upload the file to storage");
        }
    }

    public String storageFile(MultipartFile file) {

        String mimeType = file.getContentType();

        //Check file type just image
        if (mimeType == null||(!mimeType.startsWith("image/"))){
            throw new RuntimeException("Invalid Upload Image Required");
        }

        try {
            String fileName = file.getOriginalFilename();

            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Could not store " + file.getOriginalFilename() + " please check again");
        }
    }

    public Resource downloadFile(String nameFile)throws FileNotFoundException{
        try {
            Path targetLocation = this.fileStorageLocation.resolve(nameFile).normalize();
            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()){
                return resource;
            }else {
                throw new FileNotFoundException("File not found "+ nameFile);
            }
        }catch (MalformedURLException e){
            throw new FileNotFoundException("File not found "+ nameFile );
        }
    }

}
