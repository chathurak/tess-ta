package com.languagematters.tessta.db.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class DbServices {

    private final Connection connection;

    public HashMap<String, String> loadKeyVal(String sql) {
        HashMap<String, String> data = new HashMap<>();

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
            while (rs.next()) {
                data.put(rs.getString("key"), rs.getString("value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // Load one column string data
    public HashSet<String> loadValues(String sql, String columnName) {
        HashSet<String> data = new HashSet<>();

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
            while (rs.next()) {
                data.add(rs.getString(columnName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

}
