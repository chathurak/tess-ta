package com.languagematters.tessta.config;

import lombok.Setter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class MySqlConfig {

    private String mysqlUri;
    private String mysqlUser;
    private String mysqlPassword;

    @Bean
    public Connection mysqlConnection() {
        try {
            return DriverManager.getConnection(mysqlUri, mysqlUser, mysqlPassword);
        } catch (SQLException e) {
            throw new BeanCreationException("MySQL Bean", "Failed to create MySQL Bean", e);
        }
    }

}
