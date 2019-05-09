package com.languagematters.tessta.admin.service;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.languagematters.tessta.jpa.dto.UserDto;
import com.languagematters.tessta.jpa.entity.User;
import com.languagematters.tessta.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServices {

    private final UserRepository userRepository;
    private final DataSource dataSource;

    @Autowired
    public UserServices(final UserRepository userRepository, final DataSource dataSource) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }

    public boolean createUserRegular(UserDto userDto) {
        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);

        // Generate password hash
        HashFunction hf = Hashing.sha512();
        HashCode hc = hf.newHasher()
                .putString(userDto.getPassword(), Charsets.UTF_8)
                .putBytes(salt)
                .hash();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("first_name", userDto.getFirstName());
        parameters.put("last_name", userDto.getLastName());
        parameters.put("email", userDto.getEmail());
        parameters.put("password_hash", hc.asBytes());
        parameters.put("salt", salt);

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("userDto");

        return simpleJdbcInsert.execute(parameters) > 0;
    }

    public boolean createUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);

        // Generate password hash
        HashFunction hf = Hashing.sha512();
        HashCode hc = hf.newHasher()
                .putString(userDto.getPassword(), Charsets.UTF_8)
                .putBytes(salt)
                .hash();

        user.setPasswordHash(hc.asBytes());
        user.setSalt(salt);

        User resultUser = userRepository.save(user);

        return resultUser.getId() > 0;
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
