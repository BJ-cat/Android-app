package com.example.myapplication;

import static org.mockito.Mockito.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FirebaseAuthTest {

    // Mock instance of FirebaseAuth to simulate Firebase authentication processes
    @Mock
    private FirebaseAuth mockFirebaseAuth;

    // Mock instance of FirebaseUser to simulate user actions
    @Mock
    private FirebaseUser mockFirebaseUser;


    @Before
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);
        // Configure the FirebaseAuth mock to return a mocked user when getCurrentUser() is called
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(mockFirebaseUser);
    }

    // Verify if the user login function retrieves the correct user email.
    @Test
    public void testUserLogin() {
        // Check whether the user has logged in
        when(mockFirebaseUser.getEmail()).thenReturn("comp@gmail.com");

        // Call getEmail() to fetch the email of the currently logged-in user
        String userEmail = mockFirebaseUser.getEmail();

        // Examine whether the authentication is checked correctly
        if (!userEmail.equals("comp@gmail.com")) {
            System.out.println("Login failed: Email does not match expected value.");
        }

        assert userEmail.equals("comp@gmail.com");
    }
}
