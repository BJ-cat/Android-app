package com.example.myapplication.media;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadShowData {

    public static List<Resource> loadResources(String filename) {
        List<Resource> resources = new ArrayList<>();
        try {
            String jsonText = new String(Files.readAllBytes(Paths.get(filename)));
            JSONObject jsonObject = new JSONObject(jsonText);
            JSONArray jsonArray = jsonObject.getJSONArray("mental_health_resources");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonResource = jsonArray.getJSONObject(i);
                Resource resource = new Resource(
                        jsonResource.getString("name"),
                        jsonResource.getString("description"),
                        jsonResource.getString("type"),
                        jsonResource.getString("link"),
                        jsonResource.getDouble("rating")
                );
                resources.add(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resources;
    }

    public static void main(String[] args) {
        List<Resource> resources = loadResources("path_to_mental_health_resources.json");
        for (Resource resource : resources) {
            System.out.println(resource);
        }
    }
}
