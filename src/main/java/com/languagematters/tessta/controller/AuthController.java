package com.languagematters.tessta.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/signout")
    public String signout() {
        return "index.html";
    }

}
