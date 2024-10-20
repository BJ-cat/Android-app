package com.example.myapplication;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(RobolectricTestRunner.class)
public class JsonReaderTest {
    @Mock
    AssetManager mockAssetManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadJSONFromAsset_ReturnsCorrectList() throws IOException {
        // Setup
        Context context = RuntimeEnvironment.systemContext;
        when(context.getAssets()).thenReturn(mockAssetManager);
        String json = "[{\"name\":\"Tesla Model S\",\"description\":\"Electric Sedan\",\"type\":\"Electric\",\"link\":\"https://www.tesla.com/models\"}]";
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes())) {
            when(mockAssetManager.open("cars.json")).thenReturn(inputStream);

            // Execution
            List<CarInfo> carInfoList = JsonReader.loadJSONFromAsset(context, "cars.json");

            // Assertions
            assertNotNull(carInfoList);
            assertEquals(1, carInfoList.size());
            CarInfo carInfo = carInfoList.get(0);
            assertEquals("Tesla Model S", carInfo.getName());
            assertEquals("Electric Sedan", carInfo.getDescription());
            assertEquals("Electric", carInfo.getType());
            assertEquals("https://www.tesla.com/models", carInfo.link);
        }
    }

    @Test
    public void loadJSONFromAsset_HandlesIOException() throws IOException {
        // Setup
        Context context = RuntimeEnvironment.systemContext;
        when(context.getAssets()).thenReturn(mockAssetManager);
        when(mockAssetManager.open("invalid_file.json")).thenThrow(new IOException("File not found"));

        // Execution
        List<CarInfo> carInfoList = JsonReader.loadJSONFromAsset(context, "invalid_file.json");

        // Assertions
        assertTrue(carInfoList.isEmpty());
    }
}
