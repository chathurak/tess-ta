package com.languagematters.tessta.library.services;

import com.languagematters.tessta.library.services.model.UserFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryUserFileServices {

    // Connection info. TODO: Load from properties
    String URI = "jdbc:mysql://localhost:3306/tesseract_ta";
    String USER = "root";
    String PASS = "";

    public static LibraryUserFileServices service = new LibraryUserFileServices();

    private LibraryUserFileServices() {}

    public static LibraryUserFileServices getInstance() {
        return service;
    }

    public List<UserFile> getUserFiles() {
        int userId = 1; // TODO: Get userId from session
        List<UserFile> userFiles = new ArrayList<UserFile>();

        try {
            Connection conn = DriverManager.getConnection(URI, USER, PASS);

            if (conn != null) {
                String sql = "SELECT * FROM user_file";

                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);

                while (result.next()){
                    UserFile userFile = new UserFile();
                    userFile.setName(result.getString("name"));
                    userFile.setPath(result.getString("path"));
                    userFile.setIsText(result.getInt("is_text") == 1);
                    userFile.setCreatedAt(result.getDate("created_at"));
                    userFile.setUserId(result.getInt("user_id"));
                    userFiles.add(userFile);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userFiles;
    }

    public void createUserFile(UserFile userFile) {
        int userId = 1; // TODO: Get userId from session

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
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

    public void deleteUserFile(String userFileName){
        // TODO: Implement delete from the db
    }

    public void rename(String userFileName, String newName) {
        // TODO: Implement rename
    }
}

