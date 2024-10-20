package com.example.myapplication;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.content.Context;

@RunWith(RobolectricTestRunner.class)
public class ShowJsonActivityTest {
    private ShowJsonActivity activity;
    private ActivityController<ShowJsonActivity> controller;

    @Mock
    Context mockContext;
    @Mock
    RecyclerView mockRecyclerView;
    @Mock
    CarListAdapter mockAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        // Create activity controller, which drives the activity lifecycle
        controller = Robolectric.buildActivity(ShowJsonActivity.class);
        activity = controller.get();
        // Set up the mocked context and RecyclerView
        activity.context = mockContext;
        activity.recyclerView = mockRecyclerView;
        activity.mCarListAdapter = mockAdapter;
        controller.create().start().resume();
    }

    @Test
    public void activityShouldCorrectlyInitializeRecyclerView() {
        // Verify the RecyclerView setup
        verify(mockRecyclerView).setLayoutManager(any(LinearLayoutManager.class));
        verify(mockRecyclerView).setAdapter(mockAdapter);
    }
}
