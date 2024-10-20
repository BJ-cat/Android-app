package com.example.myapplication.media;

public class Resource {
    private String name;
    private String description;
    private String type;
    private String link;
    private double rating;

    // Constructor
    public Resource(String name, String description, String type, String link, double rating) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.link = link;
        this.rating = rating;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", link='" + link + '\'' +
                ", rating=" + rating +
                '}';
    }
}
