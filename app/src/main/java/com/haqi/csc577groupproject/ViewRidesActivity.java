package com.haqi.csc577groupproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haqi.csc577groupproject.adapter.RideAdapter;
import com.haqi.csc577groupproject.Ride;
import com.haqi.csc577groupproject.model.User;
import com.haqi.csc577groupproject.remote.ApiUtils;
import com.haqi.csc577groupproject.remote.RideService;
import com.haqi.csc577groupproject.sharedpref.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRidesActivity extends AppCompatActivity {

    private RideService rideService;
    private RideAdapter adapter;
    private ArrayList<Ride> rides = new ArrayList<>();
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
        rvRides.setLayoutManager(new LinearLayoutManager(this));

        // Set up adapter with empty list first
        adapter = new RideAdapter(this, rides);
        rvRides.setAdapter(adapter);

        // Get token from SharedPreferences
        SharedPrefManager spm = new SharedPrefManager(getApplicationContext());
        User user = spm.getUser();
        String token = user.getToken();

        // Get ride service instance
        rideService = ApiUtils.getRideService(); // Bug 1 fixed: was "RideService =" (capital R)

        // Execute the call
        rideService.getAllRides(token).enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                Log.d("MyApp:", "Response: " + response.raw().toString());

                if (response.code() == 200) {
                    rides.clear();
                    for (Ride ride : response.body()) {
                        if (ride.getAvailable_Seats() > 0) {
                            rides.add(ride);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ViewRidesActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ride>> call, Throwable t) {
                Toast.makeText(ViewRidesActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
                Log.e("MyApp:", t.toString());
            }
        });
    }
}