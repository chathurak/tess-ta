package com.languagematters.tessta.library.services;

import com.languagematters.tessta.library.model.UserFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServices {

    private final Connection connection;

    @Autowired
    public DocumentServices(final Connection connection) {
        this.connection = connection;
    }

    public List<UserFile> getDocuments(int userId) {
        List<UserFile> userFiles = new ArrayList<>();

        try {
            String sql = "SELECT * FROM document where user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                UserFile userFile = new UserFile();
                userFile.setId(result.getInt("id"));
                userFile.setUserId(result.getInt("user_id"));
                userFile.setName(result.getString("name"));
                userFile.setOriginalFileName(result.getString("original_file_name"));
                userFile.setCreatedAt(result.getDate("created_at"));
                userFile.setUpdatedAt(result.getDate("updated_at"));
                userFiles.add(userFile);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userFiles;
    }

    public UserFile getDocument(int documentId) {
        UserFile userFile = new UserFile();

        try {
            String sql = "SELECT * FROM document WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, documentId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                userFile.setId(result.getInt("id"));
                userFile.setUserId(result.getInt("user_id"));
                userFile.setName(result.getString("name"));
                userFile.setOriginalFileName(result.getString("original_file_name"));
                userFile.setCreatedAt(result.getDate("created_at"));
                userFile.setUpdatedAt(result.getDate("updated_at"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userFile;
    }

    public int createDocument(@NotNull UserFile userFile) {
        try {
            String sql = "INSERT INTO document (user_id, name, original_file_name, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userFile.getUserId());
            statement.setString(2, userFile.getName());
            statement.setString(3, userFile.getOriginalFileName());
            statement.setDate(4, new java.sql.Date(userFile.getCreatedAt().getTime()));
            statement.setDate(5, new java.sql.Date(userFile.getUpdatedAt().getTime()));

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

    public int deleteDocument(int documentId) {
        try {
            String sql = "DELETE FROM document WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, documentId);

            int rowsApplied = statement.executeUpdate();
            return rowsApplied;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public int renameDocument(int documentId, String newName) {
        try {
            String sql = "UPDATE document " +
                    "SET name = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newName);
            statement.setInt(2, documentId);

            int rowsApplied = statement.executeUpdate();
            return rowsApplied;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }
}

