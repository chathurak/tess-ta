package com.languagematters.tessta.web.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSummary {

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

}