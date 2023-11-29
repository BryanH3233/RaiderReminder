package com.example.raiderreminder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CalenderAdapter extends RecyclerView.Adapter<CalenderViewHolder>
{
    // List to store the days of the month
    private final ArrayList<String> daysOfMonth;
    // Listener to handle item click events
    private final OnItemListener onItemListener;
    // Constructor to initialize the adapter with data and click listener
   public CalenderAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener)
   {
        this.daysOfMonth = daysOfMonth;
       // Set a click listener on the item view
        this.onItemListener = onItemListener;
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Inflate the item layout and set its height dynamically
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calender_cell, parent,  false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        // Create a new ViewHolder with the inflated view and click listener
        return new CalenderViewHolder(view, onItemListener);
    }
    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {
        // Set the text of the day in the ViewHolder
        holder.dayOfMonth.setText(daysOfMonth.get(position));

        // Check if there are events for this day
        if (daysOfMonth.get(position).contains("\n")) {
            holder.dotView.setVisibility(View.VISIBLE); // Show the dot
        } else {
            holder.dotView.setVisibility(View.GONE); // Hide the dot
        }
        // Set a click listener for the item view
        holder.itemView.setOnClickListener(v -> {
            // Pass the clicked position and day text to the onItemClick method in the listener
            if (onItemListener != null) {
                onItemListener.onItemClick(position, daysOfMonth.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }
    // Interface to handle item click events
    public interface  OnItemListener
    {

        void onItemClick(int position, String dayText);

    }
}
