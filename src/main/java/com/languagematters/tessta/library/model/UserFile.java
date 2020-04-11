package com.languagematters.tessta.library.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFile {
    private int id;
    private int userId;
    private String name;
    private String originalFileName;
}