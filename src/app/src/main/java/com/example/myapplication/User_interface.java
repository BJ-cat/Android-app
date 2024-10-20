package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class User_interface extends AppCompatActivity {

    private ImageButton photoChange;
    private ActivityResultLauncher<String> mGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        photoChange = findViewById(R.id.photoChange);
        ImageButton backButton = findViewById(R.id.backButton);
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> photoChange.setImageURI(uri));  // Update ImageButton's image

        photoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        mGetContent.launch("image/*");  // Launch image selector
                    }
                } else {
                    mGetContent.launch("image/*");  // Launch image selector for versions below Marshmallow
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
