package com.example.myapplication;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MapsActivityTest {

    @Rule
    public ActivityScenarioRule<MapsActivity> scenarioRule = new ActivityScenarioRule<>(MapsActivity.class);

    @Mock
    GoogleMap googleMap;

    public MapsActivityTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMapInitialization() {
        scenarioRule.getScenario().onActivity(activity -> {
            OnMapReadyCallback callback = googleMap -> {

                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            };


            callback.onMapReady(googleMap);

            verify(googleMap, times(1)).setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        });
    }
}
