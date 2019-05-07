package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.admin.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/api/user/all", method = RequestMethod.GET)
    public ArrayList<Object> getAllUsers() {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(value = "/api/user/withid", method = RequestMethod.GET)
    public ArrayList<Object> getUserWithId(@RequestParam(value = "id") String id) {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(value = "/api/user/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signUpUser(@RequestParam(value = "firstName") String firstName,
                                             @RequestParam(value = "lastName") String lastName,
                                             @RequestParam(value = "email") String email,
                                             @RequestParam(value = "password") String password) {

        boolean result = userServices.addUser(firstName, lastName, email, password);

        if (result) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.format(String.format("New user successfully created : %s", email)));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(String.format(String.format("Failed to create the new user : %s", email)));
        }
    }
}
