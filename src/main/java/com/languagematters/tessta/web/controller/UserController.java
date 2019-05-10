package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.admin.service.UserServices;
import com.languagematters.tessta.jpa.dto.UserDto;
import com.languagematters.tessta.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
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
    public ResponseEntity<String> signIn(@RequestBody UserDto userDto) {

        User user = userServices.getUser(userDto.getEmail());

        UserDto userDtoResponse = new UserDto();
        userDtoResponse.setFirstName(user.getFirstName());
        userDtoResponse.setLastName(user.getLastName());
        userDtoResponse.setEmail(user.getEmail());

        var k = ResponseEntity.ok().body(user);
        System.out.println(k);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(String.format("New userDto successfully created : %s", userDto.getEmail()));
    }
}
