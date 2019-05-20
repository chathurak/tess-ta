package com.languagematters.tessta.library.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Task {
    private int id;
    private int userFileId;
    private int tessdataId;

    private Date createdAt;
    private int updatedBy;
}
