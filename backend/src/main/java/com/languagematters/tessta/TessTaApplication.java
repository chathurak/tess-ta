package com.languagematters.tessta;

import com.languagematters.tessta.config.AppProperties;
import com.languagematters.tessta.service.StorageServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TessTaApplication implements CommandLineRunner {

    @Resource
    private StorageServices storageServices;

    public static void main(String[] args) {
        SpringApplication.run(TessTaApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void run(String... arg) throws Exception {
//        storageServices.clearStorage();
        storageServices.initStorage();
    }

}