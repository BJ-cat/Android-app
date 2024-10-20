package com.example.myapplication.data.model;
import java.util.Date;
import java.util.ArrayList;

public class Post {
    private String content;
    private Date timestamp;
    private int likes;
    private ArrayList<String> comments;

    public Post(String content, Date timestamp) {
        this.content = content;
        this.timestamp = timestamp;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void like() {
        this.likes++;
    }

    public int getLikes() {
        return likes;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public ArrayList<String> getComments() {
        return comments;
    }
}
