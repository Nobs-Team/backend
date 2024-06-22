package com.nobs.banksampah.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Admin")
public class Admin {

    private String username;
    private String password;
    private String role;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}