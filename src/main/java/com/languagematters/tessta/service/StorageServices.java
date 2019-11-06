package com.languagematters.tessta.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServices {

    @Value("${app.tempstore}")
    private String tempStorePath;

    private Path rootLocation;

    @PostConstruct
    private void init() {
        rootLocation = Paths.get(tempStorePath);
    }

    public void initStorage() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            System.out.println("Could not initialize storage!");
        }
    }

    public void store(MultipartFile file, String location) {
        try {
            Files.createDirectories(rootLocation.resolve(location));
            Files.copy(file.getInputStream(), rootLocation.resolve(location + file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new UnsupportedOperationException();
                // TODO : Handle this
            }
        } catch (MalformedURLException e) {
            throw new UnsupportedOperationException();
            // TODO : Handle this
        }
    }

    public void clearStorage() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

}