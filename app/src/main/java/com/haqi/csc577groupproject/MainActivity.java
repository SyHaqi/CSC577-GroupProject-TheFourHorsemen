package com.haqi.csc577groupproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.haqi.csc577groupproject.R;
import com.haqi.csc577groupproject.model.User;
import com.haqi.csc577groupproject.sharedpref.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // TEMPORARY DISABLE LOGIN CHECK FOR TESTING
        // SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        // if (!spm.isLoggedIn()) {
        //     finish();
        //     startActivity(new Intent(this, LoginActivity.class));
        //     return;
        // }

        // TEMPORARY HARDCODED USER DISPLAY
        ((TextView) findViewById(R.id.tvUsername)).setText("TEST USER");
        ((TextView) findViewById(R.id.tvEmail)).setText("test@gmail.com");

//        User user = spm.getUser();
//        ((TextView) findViewById(R.id.tvUsername)).setText(user.getUsername().toUpperCase());
//        ((TextView) findViewById(R.id.tvEmail)).setText(user.getEmail());

        // Notification bell
        ImageButton btnNotification = findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(v -> {
            String[] messages = {
                    "🔧 Maintenance scheduled on 6/8/2026 at 12:00 AM",
                    "🚗 New ride available: UiTM → Melaka Sentral",
                    "📢 Welcome to RideShare UiTM!"
            };
            new AlertDialog.Builder(this)
                    .setTitle("Notifications")
                    .setItems(messages, null)
                    .setPositiveButton("Close", null)
                    .show();
        });

        // temporary spm for logout
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        // Logout
        ImageButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            spm.logout();
            Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // ViewRide
        ((LinearLayout) findViewById(R.id.rowRideShare)).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewRidesActivity.class)));

        // View My Bookings
        ((LinearLayout) findViewById(R.id.rowViewMyBookings)).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewBookingActivity.class)));

        // DeliveryFood — show cs.png popup
        ((LinearLayout) findViewById(R.id.rowDeliveryFood)).setOnClickListener(v ->
                showComingSoonPopup());

        // Semester Break Storage — show cs.png popup
        ((LinearLayout) findViewById(R.id.rowStorage)).setOnClickListener(v ->
                showComingSoonPopup());

        ((LinearLayout) findViewById(R.id.rowDriverHub)).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreateRideActivity.class)));

        // Settings
        ((LinearLayout) findViewById(R.id.rowUpdateProfile)).setOnClickListener(v ->
                startActivity(new Intent(this, UpdateProfileActivity.class)));
    }

    private void showComingSoonPopup() {
        // Display cs.png landscape-fitted inside the dialog
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.cs1);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);

        new AlertDialog.Builder(this)
                .setView(imageView)
                .setPositiveButton("Close", null)
                .show();
    }
}