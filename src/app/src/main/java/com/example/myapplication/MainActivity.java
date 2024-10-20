package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ImageButton searchButton;
    private ImageButton postButton;
    private MaterialButton meButton;
    private MaterialButton mapButton;
    private MaterialButton eButton;
    private MaterialButton fButton;
    private MaterialButton currentActiveButton = null;

    // Observer pattern components
    private Subject subject;
    private RemindObserver remindObserver;
    private boolean isSubscribed = false;

    // SharedPreferences for storing subscription status
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefsFile";  // specified file name
    private static final String SUBSCRIPTION_KEY = "isSubscribed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize subject and observer for notifications
        subject = new Subject();
        remindObserver = new RemindObserver(this);

        // Retrieve subscription status from SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isSubscribed = sharedPreferences.getBoolean(SUBSCRIPTION_KEY, false);

        // Check if user is logged in, if not redirect to LoginActivity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Initialize and update subscribe button based on subscription status saved
        MaterialButton subscribeButton = findViewById(R.id.subscribe_button);
        updateSubscribeButton(subscribeButton);

        // Set click listener for the subscribe button to handle subscription logic
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSubscribed) {
                    subject.subscribe(remindObserver);
                    subject.notifyObservers("You have subscribed to daily happy reminders!");
                    isSubscribed = true;
                } else {
                    subject.unsubscribe(remindObserver);
                    subject.notifyObservers("You have cancelled the subscription");
                    isSubscribed = false;
                }
                // Save the subscription status to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(SUBSCRIPTION_KEY, isSubscribed);
                editor.apply();
                updateSubscribeButton(subscribeButton);
            }
        });

        searchButton = findViewById(R.id.imageButton5);
        searchButton.setOnClickListener(view -> {
            Intent searchIntent = new Intent(MainActivity.this, Search.class);
            startActivity(searchIntent);
        });

        postButton = findViewById(R.id.button_Post);
        postButton.setOnClickListener(view -> {
            Intent postIntent = new Intent(MainActivity.this, Post.class);
            startActivity(postIntent);
        });

        mapButton = findViewById(R.id.button_map);
        mapButton.setOnClickListener(view -> {
            Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(mapIntent);
        });

        meButton = findViewById(R.id.button_Me);
        meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jump2me = new Intent(getApplicationContext(), User_interface.class);
                startActivity(jump2me);
            }
        });

        // Initialize follow and explore buttons
        fButton = findViewById(R.id.button_follow);
        eButton = findViewById(R.id.button_explore);

        // Set click listeners to handle active button switching
        fButton.setOnClickListener(v -> setActiveButton(fButton));
        eButton.setOnClickListener(v -> setActiveButton(eButton));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ContentFragment())
                    .commit();
        }

        MaterialButton buttonHome = findViewById(R.id.button_home);
        MaterialButton buttonMessage = findViewById(R.id.button_message);

        buttonHome.setOnClickListener(v -> replaceFragment(new ContentFragment()));
        buttonMessage.setOnClickListener(v -> replaceFragment(new MsgList()));

        // Load the default fragment if there is no saved instance state
        if (savedInstanceState == null) {
            replaceFragment(new ContentFragment());
        }
    }

    private void updateSubscribeButton(MaterialButton subscribeButton) {
        if (isSubscribed) {
            subscribeButton.setText("Unsubscribe");
        } else {
            subscribeButton.setText("Subscribe");
        }
    }

    private void setActiveButton(MaterialButton newActiveButton) {
        if (currentActiveButton != null) {
            currentActiveButton.setBackgroundColor(Color.TRANSPARENT);
            currentActiveButton.setTextColor(Color.BLACK);
        }

        currentActiveButton = newActiveButton;
        currentActiveButton.setBackgroundColor(Color.RED);
        currentActiveButton.setTextColor(Color.WHITE);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unsubscribe remind observer when the activity is destroyed
        subject.unsubscribe(remindObserver);
    }
}






