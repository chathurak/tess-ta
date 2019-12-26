package com.languagematters.tessta.grammar.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class DBUtils {

    @Value("${app.mysql-user}")
    private String mysqlUser;

    @Value("${app.mysql-password}")
    private String mysqlPassword;

    @Value("${app.mysql-uri}")
    private String mysqlUri;

    public HashMap<String, String> loadKeyVal(String sql) {
        HashMap<String, String> data = new HashMap<>();

        Connection conn;
        Statement stmt;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");

            conn = DriverManager.getConnection(this.mysqlUri, this.mysqlUser, this.mysqlPassword);
            System.out.println("Creating statement...");

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                data.put(rs.getString("key"), rs.getString("value"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    // Load one column string data
    public HashSet<String> loadValues(String sql, String columnName) {
        HashSet<String> data = new HashSet<>();

        Connection conn;
        Statement stmt;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");

            conn = DriverManager.getConnection(this.mysqlUri, this.mysqlUser, this.mysqlPassword);
            System.out.println("Creating statement...");

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                data.add(rs.getString(columnName));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }


    // Get Connection
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Connecting to database...");

        Connection conn = DriverManager.getConnection(this.mysqlUri, this.mysqlUser, this.mysqlPassword);
        return conn;
    }


}
