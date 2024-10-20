package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    public static List<CarInfo> loadJSONFromAsset(Context context, String filename) {
        // Initialize an empty list to hold the CarInfo objects.
        List<CarInfo> carInfoList=new ArrayList<>();

        // Check if the context is null to prevent the app from crashing due to a null pointer exception.
        if (context==null){
            Log.e("loadJSONFromAsset","context is null");
            return carInfoList ;
        }
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(filename);
            // Determine the number of bytes to read from the input stream.
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            // Convert the byte array into a string, assuming UTF-8 encoding.
            String  json = new String(buffer, StandardCharsets.UTF_8);
            // Parse the string into a JSONArray object
            JSONArray jsonArray = new JSONArray(json);
            // Get the JSONObject at position i.
            for (int i = 0; i < jsonArray.length();i++) {
                JSONObject objPoint = (JSONObject) jsonArray.opt(i);
//                Log.e("loadJSONFromAsset","objPoint:"+objPoint.toString());
                // Extract data from each object field.
                String name = objPoint.getString("name");
                String description = objPoint.getString("description");
                String type = objPoint.getString("type");
                String link = objPoint.getString("link");
                CarInfo carInfo=new CarInfo(name,description,type,link);
                carInfoList.add(carInfo);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        // Return the list of CarInfo objects populated from the JSON file.
        return carInfoList;

    }

}

