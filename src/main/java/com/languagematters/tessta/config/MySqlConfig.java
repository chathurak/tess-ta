package com.languagematters.tessta.config;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class MySqlConfig {

    private final AppProperties appProperties;

    public MySqlConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public Connection mysqlConnection() {
        try {
            return DriverManager.getConnection(appProperties.getMysql().getUri(), appProperties.getMysql().getUser(),
                    appProperties.getMysql().getPassword());
        } catch (SQLException e) {
            throw new BeanCreationException("MySQL Bean", "Failed to create MySQL Bean", e);
        }
    }

}
