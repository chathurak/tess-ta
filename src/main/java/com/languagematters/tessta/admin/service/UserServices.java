package com.languagematters.tessta.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServices {

    private final DataSource dataSource;

    @Autowired
    public UserServices(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addUser(String firstName, String lastName, String email, String password) {
        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Generate password hash
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            md.update(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        byte[] passwordHash = md.digest();

        Map<String, Object> parameters = new HashMap<>();

        // Insert password
        parameters.put("password_hash", passwordHash);
        parameters.put("salt", salt);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("password")
                .usingGeneratedKeyColumns("id");

        Number passwordId = simpleJdbcInsert.executeAndReturnKey(parameters);

        // Insert user
        parameters.clear();
        parameters.put("first_name", firstName);
        parameters.put("last_name", lastName);
        parameters.put("email", email);
        parameters.put("password_id", passwordId.intValue());

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user");

        return simpleJdbcInsert.execute(parameters) > 0;
    }

    public boolean updateUser() {
        throw new UnsupportedOperationException();
    }

    public boolean deleteUser() {
        throw new UnsupportedOperationException();
    }

}
