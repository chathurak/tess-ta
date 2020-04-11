package com.languagematters.tessta.library.services;

import com.languagematters.tessta.grammar.util.FileUtils;
import com.languagematters.tessta.library.model.UserFile;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServices {

    @Value("${app.tempstore}")
    private String tempStorePath;

    private final Connection connection;

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

    public String getDocumentContent(int documentId, String username) {
        UserFile userFile = this.getDocument(documentId);

        File originalFile = new File(String.format("%s/%s/%s/%s", this.tempStorePath, username, documentId, userFile.getOriginalFileName()));
        String text = FileUtils.loadTextFile(originalFile.getPath());

        return text;
    }
}

