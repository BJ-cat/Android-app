package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.data.model.NormalUser;
import com.example.myapplication.data.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {

    // UI components
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth instance
        auth = FirebaseAuth.getInstance();

        // Get the UI components by their IDs
        emailEditText = findViewById(R.id.text_email_address);
        passwordEditText = findViewById(R.id.text_password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        // Apply window inset processing for better UI layout handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton.setOnClickListener(v -> attemptLogin());

        // Set click listener for the register button to navigate to the RegisterActivity
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Attempts to log in the user using the provided email and password.
    private void attemptLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Use Firebase Auth to sign in with email and password
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // If login is successful, navigate to the main activity
                        Toast.makeText(LoginActivity.this, "You are now logged in", Toast.LENGTH_SHORT).show();
                        NormalUser usr = NormalUser.getInstance();
                        usr.setUsername(email);
                        usr.setEmail(email);
                        usr.setId(checkId(usr));
                        Toast.makeText(getApplicationContext(), "uid " + Integer.toString(usr.getId()), Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(getApplicationContext(), MainActivity.class);
                        login.putExtra("USR", usr);
                        startActivity(login);
                        finish();
                    } else {
                        // If login fails, display an error message
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Checks and returns the user ID from a CSV file based on the username.
     *
     * @param usr The NormalUser instance containing the username.
     * @return The user ID if found, otherwise -1.
     */
    private int checkId(NormalUser usr) {
        String uname = usr.getUsername();
        BufferedReader csvReader = null;
        int uid = -1;
        try {
            // Create a buffered reader instance to read the CSV file
            csvReader = new BufferedReader(new InputStreamReader(getAssets().open("checkid.csv"), StandardCharsets.UTF_8));
            String line;
            // Read the file line by line
            while ((line = csvReader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 2) {
                    // Check if the username matches
                    if (uname.equals(tokens[1].trim())) {
                        uid = Integer.parseInt(tokens[0].trim());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // Handle exceptions and display error messages
            Toast.makeText(getApplicationContext(), "Match id error!", Toast.LENGTH_SHORT).show();
        } finally {
            // Close the buffered reader
            try {
                if (csvReader != null) {
                    csvReader.close();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error closing the reader!", Toast.LENGTH_SHORT).show();
            }
        }
        return uid;
    }
}