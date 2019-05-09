package com.languagematters.tessta.jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
