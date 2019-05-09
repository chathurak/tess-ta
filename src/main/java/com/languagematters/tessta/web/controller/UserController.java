package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.admin.service.UserServices;
import com.languagematters.tessta.jpa.dto.UserDto;
import com.languagematters.tessta.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(final UserServices userServices) {
        this.userServices = userServices;
    }

    @RequestMapping(value = "/api/users/signup", method = RequestMethod.POST)
    public ResponseEntity<User> signUp(@Valid @RequestBody UserDto userDto) {

        User user = userServices.createUser(userDto);
        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(value = "/api/users/signin", method = RequestMethod.POST)
    public ResponseEntity<User> signIn(@RequestBody UserDto userDto) {

        User user = userServices.getUser(userDto.getEmail());
        return ResponseEntity.ok().body(user);
    }
}
