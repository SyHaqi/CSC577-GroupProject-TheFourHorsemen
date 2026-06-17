package com.haqi.csc577groupproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookRide extends AppCompatActivity {

    Button confirmBookingButton, cancelBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        confirmBookingButton = findViewById(R.id.confirmBookingButtonID);
        cancelBookingButton = findViewById(R.id.cancelBookingButtonID);

        confirmBookingButton.setOnClickListener(v -> {
            Toast.makeText(BookRide.this, "Booking confirmed", Toast.LENGTH_SHORT).show();

            // Later, insert data into bookings table here
            // user_id + ride_id
        });

        cancelBookingButton.setOnClickListener(v -> {
            finish();
        });
    }
}