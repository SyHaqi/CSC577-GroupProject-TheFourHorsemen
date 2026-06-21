package com.haqi.csc577groupproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.activity.EdgeToEdge;

public class BookRideActivity extends AppCompatActivity {

    private int seatCount = 1;
    private int availableSeats = 0;

    private TextView seatCountTextView;
    private TextView seatLimitHintTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_ride);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bookRideLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- Populate ride details from Intent ---
        String driverName    = getIntent().getStringExtra("driverName");
        String origin        = getIntent().getStringExtra("origin");
        String destination   = getIntent().getStringExtra("destination");
        String departureTime = getIntent().getStringExtra("departureTime");
        String passengerName = getIntent().getStringExtra("passengerName");
        availableSeats       = getIntent().getIntExtra("availableSeats", 0);

        ((TextView) findViewById(R.id.driverNameTextViewID)).setText("Driver: " + driverName);
        ((TextView) findViewById(R.id.originTextViewID)).setText("From: " + origin);
        ((TextView) findViewById(R.id.destinationTextViewID)).setText("To: " + destination);
        ((TextView) findViewById(R.id.departureTimeTextViewID)).setText("Departure: " + departureTime);
        ((TextView) findViewById(R.id.availableSeatsTextViewID)).setText("Available Seats: " + availableSeats);
        ((TextView) findViewById(R.id.passengerNameTextViewID)).setText("Passenger: " + passengerName);

        // --- Seat selector ---
        seatCountTextView     = findViewById(R.id.seatCountTextViewID);
        seatLimitHintTextView = findViewById(R.id.seatLimitHintTextViewID);
        Button decreaseBtn    = findViewById(R.id.decreaseSeatButtonID);
        Button increaseBtn    = findViewById(R.id.increaseSeatButtonID);
        Button confirmBtn     = findViewById(R.id.confirmBookingButtonID);
        Button cancelBtn      = findViewById(R.id.cancelBookingButtonID);

        seatLimitHintTextView.setText("Max: " + availableSeats + " seat(s) available");

        decreaseBtn.setOnClickListener(v -> {
            if (seatCount > 1) {
                seatCount--;
                seatCountTextView.setText(String.valueOf(seatCount));
            } else {
                Toast.makeText(this, "Minimum 1 seat required", Toast.LENGTH_SHORT).show();
            }
        });

        increaseBtn.setOnClickListener(v -> {
            if (seatCount < availableSeats) {
                seatCount++;
                seatCountTextView.setText(String.valueOf(seatCount));
            } else {
                Toast.makeText(this, "No more available seats", Toast.LENGTH_SHORT).show();
            }
        });

        confirmBtn.setOnClickListener(v -> {
            Toast.makeText(this,
                    "Booking confirmed for " + seatCount + " seat(s)",
                    Toast.LENGTH_SHORT).show();
            // TODO: send seatCount + ride_id + user_id to database
        });

        cancelBtn.setOnClickListener(v -> finish());
    }
}