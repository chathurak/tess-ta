package com.languagematters.tessta.admin.service;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.languagematters.tessta.admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
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

    public boolean createUser(User user) {
        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);

        // Generate password hash
        HashFunction hf = Hashing.sha512();
        HashCode hc = hf.newHasher()
                .putString(user.getPassword(), Charsets.UTF_8)
                .putBytes(salt)
                .hash();

        Map<String, Object> parameters = new HashMap<>();

        // Insert password
        parameters.put("password_hash", hc.asBytes());
        parameters.put("salt", salt);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("password")
                .usingGeneratedKeyColumns("id");

        Number passwordId = simpleJdbcInsert.executeAndReturnKey(parameters);

        // Insert user
        parameters.clear();
        parameters.put("first_name", user.getFirstName());
        parameters.put("last_name", user.getLastName());
        parameters.put("email", user.getEmail());
        parameters.put("password_id", passwordId.intValue());

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user");

        return simpleJdbcInsert.execute(parameters) > 0;
    }

    public boolean getUser() {
        throw new UnsupportedOperationException();
    }

    public boolean updateUser() {
        throw new UnsupportedOperationException();
    }

    public boolean deleteUser() {
        throw new UnsupportedOperationException();
    }

}
