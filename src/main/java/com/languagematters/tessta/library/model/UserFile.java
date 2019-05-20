package com.languagematters.tessta.library.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserFile {
    private int userId;
    private String name;

    private Date createdAt;
    private Date updatedAt;
}