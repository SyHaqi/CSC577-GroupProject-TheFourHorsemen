package com.haqi.csc577groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ViewMyRidesActivity extends AppCompatActivity {

    private Button btnCancelTrip;
    private Button btnEditTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_rides);

        // Bind symmetrical card action controls
        btnCancelTrip = findViewById(R.id.btnCancelTrip);
        btnEditTrip = findViewById(R.id.btnEditTrip);

        // Edit Trip details click router action
        btnEditTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMyRidesActivity.this, CreateRideActivity.class);

                // Bundle current card variables into key-value extras map container
                intent.putExtra("isEditing", true);
                intent.putExtra("origin", "UiTM Puncak Perdana");
                intent.putExtra("destination", "Shah Alam");
                intent.putExtra("time", "5:30 PM");
                intent.putExtra("seats", "3");

                startActivity(intent);
            }
        });

        // Cancel trip row database deletion simulation
        btnCancelTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add database row removal handlers here

                Toast.makeText(ViewMyRidesActivity.this, "Trip cancelled successfully", Toast.LENGTH_SHORT).show();

                // Prevent duplicate form trigger actions
                btnCancelTrip.setEnabled(false);
                btnEditTrip.setEnabled(false);
            }
        });
    }
}