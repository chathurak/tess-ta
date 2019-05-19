package com.languagematters.tessta.web.controller;

import com.languagematters.tessta.web.security.CurrentUser;
import com.languagematters.tessta.web.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @RequestMapping("/helloworld")
    public @ResponseBody String greeting() {
        return "Hello World";
    }

    @PostMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> process(@CurrentUser UserPrincipal currentUser) {
        throw new UnsupportedOperationException();
    }

}