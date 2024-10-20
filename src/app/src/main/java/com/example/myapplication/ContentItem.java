package com.example.myapplication;

public class ContentItem {
    private int imageResId;
    private String description;

    public ContentItem(int imageResId, String description) {
        this.imageResId = imageResId;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
}
