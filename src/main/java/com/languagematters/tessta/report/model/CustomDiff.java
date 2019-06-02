package com.languagematters.tessta.report.model;

import lombok.Getter;
import lombok.Setter;

import com.languagematters.tessta.report.google.Operation;

@Getter
@Setter
public class CustomDiff {

    private String text;
    private Operation operation;
    private CustomOperation customOperation;
    private String description;

}
