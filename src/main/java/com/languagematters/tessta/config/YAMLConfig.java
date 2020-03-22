package com.languagematters.tessta.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class YAMLConfig {

    private String tessdata;
    private String tempstore;

    private String mysqlUri;
    private String mysqlUser;
    private String mysqlPassword;

}
