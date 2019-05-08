package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.admin.model.User;
import com.languagematters.tessta.admin.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(final UserServices userServices) {
        this.userServices = userServices;
    }

    @RequestMapping(value = "/api/users/all", method = RequestMethod.GET)
    public ArrayList<Object> getAll() {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(value = "/api/users/withid", method = RequestMethod.GET)
    public ArrayList<Object> getById(@RequestParam(value = "id") String id) {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(value = "/api/users/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signUp(@RequestBody User user) {

        boolean result = userServices.createUser(user);

        if (result) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.format("New user successfully created : %s", user.getEmail()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(String.format("Failed to create the new user : %s", user.getEmail()));
        }
    }
}
