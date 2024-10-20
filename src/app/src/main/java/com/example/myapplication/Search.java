package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Create the search activity
 * including onCreate and onClick activity to read the search input
 * and make it valid to jump to show data activity.
 * @author Qifeng Zheng, Bei Jin
 */

public class Search extends AppCompatActivity {
    List<CarInfo> carInfoList = new ArrayList<>();
    Context context;
    static String userInput;
    private MaterialButton currentActiveButton ;

    private boolean isActive = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);// Enable edge-to-edge display mode.
        setContentView(R.layout.activity_search);
        context = this;

        // Apply padding from system bars to the 'main' layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText input = findViewById(R.id.input);
        ImageButton search = findViewById(R.id.Search_Button);
        // Setup search button click listener to process search input and navigate to the ShowJsonActivity.
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = input.getText().toString().toLowerCase();
                Intent jump2showData =  new Intent(getApplicationContext(), ShowJsonActivity.class);
                jump2showData.putExtra("input", userInput);

                startActivity(jump2showData);
            }
        });

        // Setup listeners for predefined search buttons to insert specific search tags and navigate.
        MaterialButton articleButton = findViewById(R.id.article_button);
        articleButton.setOnClickListener(view ->{
            userInput = "#article";
            Intent jump2showData =  new Intent(getApplicationContext(), ShowJsonActivity.class);
            jump2showData.putExtra("input", userInput);
            startActivity(jump2showData);
        });

        MaterialButton videoButton = findViewById(R.id.video_button);
        videoButton.setOnClickListener(view ->{
            userInput = "#video";
            Intent jump2showData =  new Intent(getApplicationContext(), ShowJsonActivity.class);
            jump2showData.putExtra("input", userInput);
            startActivity(jump2showData);
        });

        MaterialButton cancelButton = findViewById(R.id.cancel_button);
        // Setup the cancel button to return to MainActivity and finish the current activity.
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }


    private void setActiveButton(MaterialButton newActiveButton) {
        // Toggle the background and text color of the button based on its current state.
        if (currentActiveButton != null && currentActiveButton == newActiveButton) {

            if (isActive) {

                newActiveButton.setBackgroundColor(Color.GRAY);
                newActiveButton.setTextColor(Color.BLACK);
                isActive = false;
            } else {

                newActiveButton.setBackgroundColor(Color.BLACK);
                newActiveButton.setTextColor(Color.WHITE);
                isActive = true;
            }
        } else {

            if (currentActiveButton != null) {

                currentActiveButton.setBackgroundColor(Color.GRAY);
                currentActiveButton.setTextColor(Color.BLACK);
            }

            newActiveButton.setBackgroundColor(Color.BLACK);
            newActiveButton.setTextColor(Color.WHITE);
            isActive = true;
        }
        currentActiveButton = newActiveButton;
    }
}