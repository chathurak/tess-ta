package com.languagematters.tessta.library.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Tessdata {
    private int id;
    private String name;

    private Date createdAt;
    private int createdBy;
    private Date updatedAt;
    private int updatedBy;
}
