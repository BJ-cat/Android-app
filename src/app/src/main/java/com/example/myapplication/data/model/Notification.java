package com.example.myapplication.data.model;
import java.util.Date;

public class Notification {
    private String type;
    private String message;
    private Date time;

    public Notification(String type, String message, Date time) {
        this.type = type;
        this.message = message;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
