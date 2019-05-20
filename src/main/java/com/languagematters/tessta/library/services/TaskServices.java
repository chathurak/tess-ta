package com.languagematters.tessta.library.services;

import com.languagematters.tessta.library.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServices {

    @Value("${app.mysql.uri}")
    private String mysqlUri;

    @Value("${app.mysql.user}")
    private String mysqlUser;

    @Value("${app.mysql.password}")
    private String mysqlPassword;

    public List<Task> getTasks(String userFileName) {
        int userId = 1; // TODO: Get userId from session
        List<Task> tasks = new ArrayList<Task>();

        try {
            Connection conn = DriverManager.getConnection(mysqlUri, mysqlUser, mysqlPassword);

            // TODO :
            if (conn != null) {
                String sql = String.format("SELECT * FROM task INNER JOIN user_file ON task.user_file_id = user_file.id " +
                        "INNER JOIN tessdata ON task.tessdata_id = tessdata.id WHERE user_file.name = '%s'", userFileName) ;
                System.out.println(sql);

                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);

                while (result.next()){
//                    Task task = new Task();
//                    task.setKey(result.getString("key"));
//                    task.setUserFileId(result.getInt("user_file_id"));
//                    task.setCreatedAt(result.getDate("created_at"));
//                    task.setAccuracy(result.getDouble("accuracy"));
//                    task.setTessdataName(result.getString("tessdata.name"));
//                    tasks.add(task);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public void createTask(Task task) {
        int userId = 1; // TODO: Get userId from session

        try {
            Connection conn = DriverManager.getConnection(mysqlUri, mysqlUser, mysqlPassword);

            // TODO :
            if (conn != null) {
                String sql = "INSERT INTO task (key, user_file_id, created_at, accuracy, tessdata_id) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(sql);

//                statement.setString(1, task.getKey());
//                statement.setInt(2, task.getUserFileId());
//                statement.setDate(3, new java.sql.Date(task.getCreatedAt().getTime()));
//                statement.setDouble(4, task.getAccuracy());
//                statement.setInt(5, task.getTessdataId());

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

