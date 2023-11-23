package com.example.raiderreminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    // TextView to display the day of the month
    public final TextView dayOfMonth;
    // Listener to handle item click events
    private final CalenderAdapter.OnItemListener onItemListener;
    // Constructor to initialize the ViewHolder with the item view and click listener
    public CalenderViewHolder(@NonNull View itemView, CalenderAdapter.OnItemListener onItemListener)
    {
        super(itemView);
        // Find the TextView in the item layout
        dayOfMonth = itemView.findViewById(R.id.cellDaytext);
        // Set the click listener for the item view
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }
    // Handle click events on the item view
    @Override
    public void onClick(View view)
    {
        // Pass the clicked position and day text to the onItemClick method in the listener
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
