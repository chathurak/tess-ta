package com.languagematters.tessta.library.services;

import com.languagematters.tessta.config.AppProperties;
import com.languagematters.tessta.grammar.util.FileUtils;
import com.languagematters.tessta.library.model.Task;
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
public class TaskServices {

    private final Connection connection;
    private final AppProperties appProperties;

    public TaskServices(Connection connection, AppProperties appProperties) {
        this.connection = connection;
        this.appProperties = appProperties;
    }

    public List<Task> getTasks(int documentId) {
        List<Task> tasks = new ArrayList<>();

        try {
            String sql = "SELECT * FROM task " +
                    "INNER JOIN document ON task.document_id = document.id " +
                    "INNER JOIN tessdata ON task.tessdata_id = tessdata.id " +
                    "WHERE document.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, documentId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Task task = new Task();
                task.setId(result.getInt("id"));
                task.setName(result.getString("task.name"));
                task.setDocumentId(result.getInt("document_id"));
                task.setTessdataId(result.getInt("tessdata_id"));
                task.setTessdataName(result.getString("tessdata.name"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public Task getTask(int taskId) {
        Task task = new Task();

        try {
            String sql = "SELECT * FROM task " +
                    "INNER JOIN document ON task.document_id = document.id " +
                    "INNER JOIN tessdata ON task.tessdata_id = tessdata.id " +
                    "WHERE task.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                task.setId(result.getInt("id"));
                task.setName(result.getString("task.name"));
                task.setDocumentId(result.getInt("document_id"));
                task.setTessdataId(result.getInt("tessdata_id"));
                task.setTessdataName(result.getString("tessdata.name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return task;
    }

    public int createTask(Task task) {
        try {
            String sql = "INSERT INTO task (document_id, tessdata_id, name) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, task.getDocumentId());
            statement.setInt(2, task.getTessdataId());
            statement.setString(3, task.getName());

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

    public String getTaskOutputContent(int taskId, String username) {
        Task task = this.getTask(taskId);
        File originalFile = new File(String.format("%s/%s/%s/%s/Tesseract_output.txt", appProperties.getStore().getTempstore(), username, task.getDocumentId(), task.getName()));
        return FileUtils.loadTextFile(originalFile.getPath());
    }
}

