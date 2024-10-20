package com.example.myapplication.data.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    int id;
    protected static String username;
    protected static String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        id = -1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Abstract methods that must be implemented by subclasses
    public abstract void sendMessage(User user, String message);

    // Common method to all users
    public void follow(User user) {
        System.out.println(this.username + " is now following " + user.getUsername());
    }
}
