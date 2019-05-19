package com.languagematters.tessta.library.services;

import com.languagematters.tessta.library.services.model.UserFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibraryUserFileServices {

    String URI = "jdbc:mysql://localhost:3306/tesseract_ta";
    String USER = "root";
    String PASS = "";

    public static LibraryUserFileServices service = new LibraryUserFileServices();

    private LibraryUserFileServices() {}

    public static LibraryUserFileServices getInstance() {
        return service;
    }

    public void getUserFiles() {
        int userId = 1; // TODO: Get userId from session

        try {
            Connection conn = DriverManager.getConnection(URI, USER, PASS);

            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createUserFile(UserFile userFile) {
        int userId = 1; // TODO: Get userId from session

        // TODO: Temp
        userFile = new UserFile();
        userFile.setName("Silumina");
        userFile.setPath("/home/silumina.txt");
        userFile.setIsText(true);
        userFile.setCreatedAt(new java.util.Date());
        userFile.setUserId(1);

        try {
            Connection conn = DriverManager.getConnection(URI, USER, PASS);

            if (conn != null) {
                String sql = "INSERT INTO user_file (name, path, is_text, created_at, user_id) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setString(1, userFile.getName());
                statement.setString(2, userFile.getPath());
                statement.setInt(3, (userFile.getIsText() ? 1 : 0));
                statement.setDate(4, new java.sql.Date(userFile.getCreatedAt().getTime()));
                statement.setInt(5, userFile.getUserId());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new user was inserted successfully!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }
}

