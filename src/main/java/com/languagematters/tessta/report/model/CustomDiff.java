package com.languagematters.tessta.report.model;

import com.languagematters.tessta.report.google.Operation;

public class CustomDiff {

    private String text;
    private Operation operation;
    private CustomOperation customOperation;
    private String description;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public CustomOperation getCustomOperation() {
        return customOperation;
    }

    public void setCustomOperation(CustomOperation customOperation) {
        this.customOperation = customOperation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
