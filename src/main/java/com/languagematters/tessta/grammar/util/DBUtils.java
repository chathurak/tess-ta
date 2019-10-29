package com.languagematters.tessta.grammar.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

public class DBUtils {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tesseract_ta"; // TODO: Load HOST as a property

    private static final String USER = "tessuser";
    private static final String PASS = "tesseract-ta@999";

    public static HashMap<String, String> loadKeyVal(String sql) {
        HashMap<String, String> data = new HashMap<>();

        Connection conn;
        Statement stmt;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
    public static HashSet<String> loadValues(String sql, String columnName) {
        HashSet<String> data = new HashSet<>();

        Connection conn;
        Statement stmt;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
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


}
