package com.haqi.csc577groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateRideActivity extends AppCompatActivity {

    private EditText edtOrigin, edtDestination, edtTime, edtSeats;
    private Button btnPublishRide, btnNavToViewMyRides;
    private boolean isEditingMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

        // Bind layout views
        edtOrigin = findViewById(R.id.edtOrigin);
        edtDestination = findViewById(R.id.edtDestination);
        edtTime = findViewById(R.id.edtTime);
        edtSeats = findViewById(R.id.edtSeats);
        btnPublishRide = findViewById(R.id.btnPublishRide);
        btnNavToViewMyRides = findViewById(R.id.btnNavToViewMyRides);

        // Check if intent parameters specify editing an existing trip
        if (getIntent().hasExtra("isEditing")) {
            isEditingMode = getIntent().getBooleanExtra("isEditing", false);

            // Prefill form input elements
            edtOrigin.setText(getIntent().getStringExtra("origin"));
            edtDestination.setText(getIntent().getStringExtra("destination"));
            edtTime.setText(getIntent().getStringExtra("time"));
            edtSeats.setText(getIntent().getStringExtra("seats"));

            // Update button text action name
            btnPublishRide.setText("Save Changes");
        }

        // Form submission click listener
        btnPublishRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRideSubmission();
            }
        });

        btnNavToViewMyRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRideActivity.this, ViewMyRidesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleRideSubmission() {
        String origin = edtOrigin.getText().toString().trim();
        String destination = edtDestination.getText().toString().trim();
        String time = edtTime.getText().toString().trim();
        String seats = edtSeats.getText().toString().trim();

        // Validation Check
        if (origin.isEmpty() || destination.isEmpty() || time.isEmpty() || seats.isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditingMode) {
            // TODO: Add database update execution queries here
            Toast.makeText(this, "Ride changes saved!", Toast.LENGTH_SHORT).show();
        } else {
            // TODO: Add database insert insertion queries here
            Toast.makeText(this, "Ride published successfully!", Toast.LENGTH_SHORT).show();
        }

        finish(); // Returns user back to dashboard interface
    }
}