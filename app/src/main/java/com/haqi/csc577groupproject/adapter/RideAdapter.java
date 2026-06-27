package com.haqi.csc577groupproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haqi.csc577groupproject.BookRideActivity;
import com.haqi.csc577groupproject.R;
import com.haqi.csc577groupproject.model.Ride;

import java.util.List;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.ViewHolder> {

    private List<Ride> rideList;
    private Context context;

    public RideAdapter(Context context, List<Ride> rideList) {
        this.context = context;
        this.rideList = rideList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_ride_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ride ride = rideList.get(position);

        holder.tvRoute.setText(ride.getOrigin() + " → " + ride.getDestination());
        holder.tvDeparture.setText("Departure: " + ride.getDeparture_Time());
        holder.tvSeats.setText("Available Seats: " + ride.getAvailable_Seats());

        // Tap the whole card → open BookRideActivity (details + booking)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookRideActivity.class);
            intent.putExtra("rideId",         ride.getRide_id());
            intent.putExtra("driverId",       ride.getDriver_id());
            intent.putExtra("origin",         ride.getOrigin());
            intent.putExtra("destination",    ride.getDestination());
            intent.putExtra("departureTime",  ride.getDeparture_Time());
            intent.putExtra("availableSeats", ride.getAvailable_Seats());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvRoute;
        TextView tvDeparture;
        TextView tvSeats;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvDeparture = itemView.findViewById(R.id.tvDeparture);
            tvSeats = itemView.findViewById(R.id.tvSeats);
        }
    }
}