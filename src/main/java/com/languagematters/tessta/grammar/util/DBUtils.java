package com.languagematters.tessta.grammar.util;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class DBUtils {

    private final Connection connection;

    public DBUtils(Connection connection) {
        this.connection = connection;
    }

    public HashMap<String, String> loadKeyVal(String sql) {
        HashMap<String, String> data = new HashMap<>();

        Statement statement;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("SET NAMES 'utf8'");
            statement.executeUpdate("SET CHARACTER SET utf8");
            statement.executeUpdate("SET SESSION collation_connection = 'utf8_unicode_ci'");

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                data.put(rs.getString("key"), rs.getString("value"));
            }

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // Load one column string data
    public HashSet<String> loadValues(String sql, String columnName) {
        HashSet<String> data = new HashSet<>();

        Statement statement;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("SET NAMES 'utf8'");
            statement.executeUpdate("SET CHARACTER SET utf8");
            statement.executeUpdate("SET SESSION collation_connection = 'utf8_unicode_ci'");

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                data.add(rs.getString(columnName));
            }

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
