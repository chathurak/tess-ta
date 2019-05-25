package com.languagematters.tessta.library.services;

import com.languagematters.tessta.library.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServices {

    private final Connection connection;

    @Autowired
    public TaskServices(final Connection connection) {
        this.connection = connection;
    }

    public List<Task> getTasks(int userFileId) {
        List<Task> tasks = new ArrayList<>();

        try {
            String sql = "SELECT * FROM task INNER JOIN user_file ON task.user_file_id = user_file.id " +
                    "INNER JOIN tessdata ON task.tessdata_id = tessdata.id WHERE user_file.name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userFileId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Task task = new Task();
                task.setId(result.getInt("id"));
                task.setUserFileId(result.getInt("user_file_id"));
                task.setTessdataId(result.getInt("tessdata_id"));
                task.setCreatedAt(result.getDate("created_at"));
                task.setUpdatedAt(result.getDate("updated_at"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public int createTask(Task task) {
        try {
            String sql = "INSERT INTO task (user_file_id, tessdata_id, created_at) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, task.getUserFileId());
            statement.setInt(2, task.getTessdataId());
            statement.setDate(3, new java.sql.Date(task.getCreatedAt().getTime()));

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

