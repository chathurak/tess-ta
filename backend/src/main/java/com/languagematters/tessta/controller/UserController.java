package com.languagematters.tessta.controller;

import com.languagematters.tessta.security.CurrentUser;
import com.languagematters.tessta.security.UserPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getEmail(), currentUser.getName());
        return userSummary;
    }

    class UserSummary {
        private String email;
        private String name;

        public UserSummary(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }
    }

}