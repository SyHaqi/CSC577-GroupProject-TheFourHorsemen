package com.haqi.csc577groupproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haqi.csc577groupproject.model.Ride;

import java.util.ArrayList;

public class ViewRidesActivity extends AppCompatActivity {

    RecyclerView rvRides;

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

        rvRides = findViewById(R.id.rvRides);

        ArrayList<Ride> rides = new ArrayList<>();

        rides.add(new Ride(
                "UiTM Puncak Perdana → Shah Alam",
                "5:30 PM",
                3));

        rides.add(new Ride(
                "Subang → UiTM Puncak Perdana",
                "8:00 AM",
                2));

        rides.add(new Ride(
                "UiTM Puncak Perdana → Melaka Sentral",
                "6:00 PM",
                4));

        rvRides.setLayoutManager(new LinearLayoutManager(this));

        RideAdapter adapter = new RideAdapter(this, rides);

        rvRides.setAdapter(adapter);
    }
}