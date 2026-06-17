package com.haqi.csc577groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewRidesActivity extends AppCompatActivity {

    Button bookRideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_rides);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewRidesLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookRideButton = findViewById(R.id.bookBtn);

        bookRideButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewRidesActivity.this, BookRideActivity.class);
            startActivity(intent);
        });
    }
}