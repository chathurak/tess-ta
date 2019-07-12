package com.languagematters.tessta.library.services;

import com.languagematters.tessta.grammar.util.FileUtils;
import com.languagematters.tessta.library.model.Task;
import com.languagematters.tessta.library.model.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServices {

    @Value("${app.tempstore}")
    private String tempStorePath;

    private final Connection connection;

    @Autowired
    public TaskServices(final Connection connection) {
        this.connection = connection;
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
                task.setCreatedAt(result.getDate("created_at"));
                task.setUpdatedAt(result.getDate("updated_at"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public Task getTask(int taskId){
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
                task.setCreatedAt(result.getDate("created_at"));
                task.setUpdatedAt(result.getDate("updated_at"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return task;
    }

    public int createTask(Task task) {
        try {
            String sql = "INSERT INTO task (document_id, tessdata_id, created_at, updated_at) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, task.getDocumentId());
            statement.setInt(2, task.getTessdataId());
            statement.setDate(3, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(4, new java.sql.Date(task.getUpdatedAt().getTime()));

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

    public String getTaskOutputContent(int taskId, String username){
        Task task = this.getTask(taskId);
        File originalFile = new File(String.format("%s/%s/%s/%s/output.txt", this.tempStorePath, username, task.getDocumentId(), task.getName()));
        return FileUtils.loadTextFile(originalFile.getPath());
    }
}

