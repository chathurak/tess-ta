package com.languagematters.tessta.report.model;

import com.languagematters.tessta.report.google.Operation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomDiff {

    private String text;
    private Operation operation;
    private CustomOperation customOperation;
    private String description;

}
