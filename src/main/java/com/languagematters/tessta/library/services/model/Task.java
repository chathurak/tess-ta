package com.languagematters.tessta.library.services.model;

import java.util.Date;

public class Task {
    String key;
    int userFileId;
    Date createdAt;
    Double Accuracy;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getUserFileId() {
        return userFileId;
    }

    public void setUserFileId(int userFileId) {
        this.userFileId = userFileId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(Double accuracy) {
        Accuracy = accuracy;
    }
}
