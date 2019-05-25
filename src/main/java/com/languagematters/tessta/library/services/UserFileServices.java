package com.languagematters.tessta.library.services;

import com.languagematters.tessta.library.model.UserFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserFileServices {

    private final Connection connection;

    @Autowired
    public UserFileServices(final Connection connection) {
        this.connection = connection;
    }

    public List<UserFile> getUserFiles(int userId) {
        List<UserFile> userFiles = new ArrayList<>();

        try {
            String sql = "SELECT * FROM user_file where user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                UserFile userFile = new UserFile();
                userFile.setId(result.getInt("id"));
                userFile.setUserId(result.getInt("user_id"));
                userFile.setName(result.getString("name"));
                userFile.setCreatedAt(result.getDate("created_at"));
                userFile.setUpdatedAt(result.getDate("updated_at"));
                userFiles.add(userFile);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userFiles;
    }

    public int createUserFile(@NotNull UserFile userFile) {
        try {
            String sql = "INSERT INTO user_file (user_id, name, created_at, updated_at) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userFile.getUserId());
            statement.setString(2, userFile.getName());
            statement.setDate(3, new java.sql.Date(userFile.getCreatedAt().getTime()));
            statement.setDate(4, new java.sql.Date(userFile.getUpdatedAt().getTime()));

            int rowsInserted = statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public void deleteUserFile(String userFileName) {
        // TODO
    }

    public void rename(String userFileName, String newName) {
        // TODO
    }
}

