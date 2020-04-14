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
        UserSummary userSummary = new UserSummary(currentUser.getId(),
                currentUser.getEmail(),
                currentUser.getName(),
                currentUser.getAttributes().get("imageUrl").toString());
        return userSummary;
    }

    class UserSummary {
        private Long id;
        private String email;
        private String name;
        private String imageUrl;

        public UserSummary(Long id, String email, String name, String imageUrl) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.imageUrl = imageUrl;
        }
    }

}