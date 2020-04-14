package com.languagematters.tessta.security;

import java.util.Map;

public class GoogleOAuth2UserInfo {

    private String id;
    private String name;
    private String email;
    private String imageUrl;

    public GoogleOAuth2UserInfo() {
    }

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        this.id = (String) attributes.get("sub");
        this.name = (String) attributes.get("name");
        this.email = (String) attributes.get("email");
        this.imageUrl = (String) attributes.get("picture");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}