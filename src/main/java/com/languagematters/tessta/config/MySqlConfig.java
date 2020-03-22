package com.languagematters.tessta.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
@Configuration
public class MySqlConfig {

    private final YAMLConfig config;

    @Bean
    public Connection mysqlConnection() {
        try {
            return DriverManager.getConnection(config.getMysqlUri(), config.getMysqlUser(), config.getMysqlPassword());
        } catch (SQLException e) {
            throw new BeanCreationException("MySQL Bean", "Failed to create MySQL Bean", e);
        }
    }

}
