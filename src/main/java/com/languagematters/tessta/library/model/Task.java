package com.languagematters.tessta.library.model;

public class Task {
    private int id;
    private String name;
    private int documentId;
    private int tessdataId;
    private String tessdataName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getTessdataId() {
        return tessdataId;
    }

    public void setTessdataId(int tessdataId) {
        this.tessdataId = tessdataId;
    }

    public String getTessdataName() {
        return tessdataName;
    }

    public void setTessdataName(String tessdataName) {
        this.tessdataName = tessdataName;
    }
}
