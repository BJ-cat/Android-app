package com.example.myapplication;

public class CarInfo {
    public String name;
    public String description;
    public String type;
    public String link;

    public CarInfo( String name,String description, String type,String link)
    {
        this.name=name;
        this.description=description;
        this.type=type;
        this.link=link;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
}
