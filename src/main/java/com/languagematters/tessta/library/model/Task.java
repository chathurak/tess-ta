package com.languagematters.tessta.library.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Task {
    private int id;
    private String name;
    private int documentId;
    private int tessdataId;
    private String tessdataName;

    private Date createdAt;
    private Date updatedAt;
}
