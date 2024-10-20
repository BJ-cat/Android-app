package com.example.myapplication;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.button.MaterialButton;
import com.google.maps.android.PolyUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;





public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlacesClient placesClient;
    private EditText mSearchField;
    private ImageButton searchButton;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyCfV2d_a90YL0eE3va2K-Tqq81azgrzfBA");

        // Create a new Places client instance
        placesClient = Places.createClient(this);

        mSearchField = findViewById(R.id.search_field);
        searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(v -> performSearch(mSearchField.getText().toString()));

        MaterialButton cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Check for location permission
        checkLocationPermission();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check if we have location permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            checkLocationPermission();
        }

        // Enable zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);


        LatLng defaultLocation = new LatLng(-35.27, 149.12); // 示例：澳大利亚悉尼
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
    }

    private void performSearch(String query) {
        // Create a request object and set the query and the fields needed
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setCountries("AU") // Optional: set country to restrict search
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                Log.i(TAG, prediction.getPlaceId() + " " + prediction.getPrimaryText(null).toString());

                // Assuming the prediction has the LAT_LNG field available.
                fetchPlaceDetails(prediction.getPlaceId(), placeFields);
            }
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + apiException.getMessage());
            }
        });
    }

    private void fetchPlaceDetails(String placeId, List<Place.Field> fields) {
        PlacesClient placesClient = Places.createClient(this);
        FetchPlaceRequest placeRequest = FetchPlaceRequest.builder(placeId, fields).build();
        placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            Log.i(TAG, "Place found: " + place.getName());
            // Move the camera to the place and add a marker
            if (place.getLatLng() != null) {
                LatLng currentLocation = new LatLng(-35.27, 149.12);// Anu
                getDirections(currentLocation, place.getLatLng());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
            }
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + apiException.getMessage());
            }
        });
    }

    private void getDirections(LatLng startLatLng, LatLng endLatLng) {
        String apiKey = "AIzaSyCfV2d_a90YL0eE3va2K-Tqq81azgrzfBA";
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + startLatLng.latitude + "," + startLatLng.longitude +
                "&destination=" + endLatLng.latitude + "," + endLatLng.longitude + "&mode=walking&key=" + apiKey;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String jsonData = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            drawPolyline(jsonData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });

    }

    private void drawPolyline(String jsonData) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray routes = jsonObject.getJSONArray("routes");
        if (routes.length() > 0) {
            JSONObject route = routes.getJSONObject(0);
            JSONObject poly = route.getJSONObject("overview_polyline");
            String polyline = poly.getString("points");
            List<LatLng> list = PolyUtil.decode(polyline);
            PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);
            for (LatLng latLng : list) {
                options.add(latLng);
            }
            mMap.addPolyline(options);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION_REQUEST_CODE);
                        })
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Always call the superclass method first

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}


