package com.languagematters.tessta.library.services;

import com.languagematters.tessta.config.AppProperties;
import com.languagematters.tessta.grammar.util.FileUtils;
import com.languagematters.tessta.library.model.UserFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServices {

    private final Connection connection;
    private final AppProperties appProperties;

    public DocumentServices(Connection connection, AppProperties appProperties) {
        this.connection = connection;
        this.appProperties = appProperties;
    }

    public List<UserFile> getDocuments(long userId) {
        List<UserFile> userFiles = new ArrayList<>();

        try {
            String sql = "SELECT * FROM document where user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);

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

    public UserFile getDocument(long documentId) {
        UserFile userFile = new UserFile();

        try {
            String sql = "SELECT * FROM document WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, documentId);

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
            String sql = "INSERT INTO document (user_id, name, original_file_name) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userFile.getUserId());
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

        File originalFile = new File(String.format("%s/%s/%s/%s", appProperties.getStore().getTempstore(), username, documentId, userFile.getOriginalFileName()));
        String text = FileUtils.loadTextFile(originalFile.getPath());

        return text;
    }
}

