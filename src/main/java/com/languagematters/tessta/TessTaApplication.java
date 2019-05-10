package com.languagematters.tessta;

import com.languagematters.tessta.web.service.StorageServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class TessTaApplication implements CommandLineRunner {

    @Resource
    StorageServices storageServices;

    public static void main(String[] args) {
        SpringApplication.run(TessTaApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageServices.clearStorage();
        storageServices.initStorage();
    }

}