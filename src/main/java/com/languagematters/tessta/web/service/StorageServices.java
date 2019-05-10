package com.languagematters.tessta.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServices {

    @Value("${app.tempstore}")
    private String tempStorePath;

    public void initStorage() {
        try {
            Path rootLocation = Paths.get(tempStorePath);
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    public void store(MultipartFile file, String pid) {
        try {
            Path rootLocation = Paths.get(tempStorePath);
            Files.createDirectory(rootLocation.resolve(pid));
            Files.copy(file.getInputStream(), rootLocation.resolve(pid + "/" + file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path rootLocation = Paths.get(tempStorePath);
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void clearStorage() {
        Path rootLocation = Paths.get(tempStorePath);
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

}