package com.haqi.csc577groupproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.haqi.csc577groupproject.model.User;
import com.haqi.csc577groupproject.remote.ApiUtils;
import com.haqi.csc577groupproject.Booking;
import com.haqi.csc577groupproject.remote.BookingService;
import com.haqi.csc577groupproject.remote.UserService;
import com.haqi.csc577groupproject.sharedpref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRideActivity extends AppCompatActivity {

    private int seatCount = 1;
    private int availableSeats = 0;
    public Ride currentRide;
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

        int rideId = getIntent().getIntExtra("rideId", -1);

        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        User user = spm.getUser();
        String token = user.getToken();

        seatCountTextView     = findViewById(R.id.seatCountTextViewID);
        seatLimitHintTextView = findViewById(R.id.seatLimitHintTextViewID);
        Button decreaseBtn    = findViewById(R.id.decreaseSeatButtonID);
        Button increaseBtn    = findViewById(R.id.increaseSeatButtonID);
        Button confirmBtn     = findViewById(R.id.confirmBookingButtonID);
        Button cancelBtn      = findViewById(R.id.cancelBookingButtonID);

        // fetch ride details
        UserService userService = ApiUtils.getUserService();
        ApiUtils.getRideService().getRide(token, rideId).enqueue(new Callback<Ride>() {

            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
                if (response.code() == 200) {
                    currentRide = response.body();
                    availableSeats = currentRide.getAvailable_Seats();

                    // fetch driver name
                    userService.getUser(token, currentRide.getDriver_id()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200) {
                                ((TextView) findViewById(R.id.driverNameTextViewID)).setText("Driver: " + response.body().getUsername());
                            } else {
                                ((TextView) findViewById(R.id.driverNameTextViewID)).setText("Driver: Unknown");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            ((TextView) findViewById(R.id.driverNameTextViewID)).setText("Driver: Unknown");
                        }
                    });

                    ((TextView) findViewById(R.id.originTextViewID)).setText("From: " + currentRide.getOrigin());
                    ((TextView) findViewById(R.id.destinationTextViewID)).setText("To: " + currentRide.getDestination());
                    ((TextView) findViewById(R.id.departureTimeTextViewID)).setText("Departure: " + currentRide.getDeparture_Time());
                    ((TextView) findViewById(R.id.availableSeatsTextViewID)).setText("Available Seats: " + availableSeats);
                    seatLimitHintTextView.setText("Max: " + availableSeats + " seat(s) available");

                    Button btnViewMap = findViewById(R.id.btnViewMap);
                    btnViewMap.setOnClickListener(v -> {
                        String query = currentRide.getOrigin() + " to " + currentRide.getDestination();
                        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(query));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        } else {
                            // fallback if Maps app not installed
                            Uri browserUri = Uri.parse("https://maps.google.com/?q=" + Uri.encode(query));
                            startActivity(new Intent(Intent.ACTION_VIEW, browserUri));
                        }
                    });

                } else if (response.code() == 401) {
                    Toast.makeText(BookRideActivity.this, "Invalid session. Please login again.", Toast.LENGTH_SHORT).show();
                    spm.logout();
                    finish();
                    startActivity(new Intent(BookRideActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(BookRideActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                Log.e("BookRide:", t.toString());
                Toast.makeText(BookRideActivity.this, "Failed to load ride details.", Toast.LENGTH_SHORT).show();
            }
        });

        // seat selector buttons
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
            if (currentRide == null) {
                Toast.makeText(this, "Ride not loaded yet!", Toast.LENGTH_SHORT).show();
                return;
            }

            BookingService bookingService = ApiUtils.getBookingService();
            bookingService.createBooking(token, spm.getUser().getId(), rideId, seatCount).enqueue(new Callback<Booking>() {
                @Override
                public void onResponse(Call<Booking> call, Response<Booking> response) {
                    if (response.code() == 200 || response.code() == 201) {
                        Toast.makeText(BookRideActivity.this,
                                "Booking confirmed for " + seatCount + " seat(s)!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(BookRideActivity.this,
                                "Booking failed: " + response.message(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Booking> call, Throwable t) {
                    Toast.makeText(BookRideActivity.this,
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        cancelBtn.setOnClickListener(v -> finish());
    }
}