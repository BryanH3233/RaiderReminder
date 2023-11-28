package com.example.raiderreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class AllEventsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allevents, container, false);

        // Get the TextViews from the layout
        TextView textView1 = view.findViewById(R.id.textView);
        TextView textView2 = view.findViewById(R.id.textView2);
        TextView textView3 = view.findViewById(R.id.textView3);
        TextView textView4 = view.findViewById(R.id.textView4);
        TextView textView5 = view.findViewById(R.id.textView5);
        TextView textView6 = view.findViewById(R.id.textView6);
        TextView textView7 = view.findViewById(R.id.textView7);
        TextView textView8 = view.findViewById(R.id.textView8);

        // Retrieve the events from the EventManager
        List<eventClass> events = EventManager.getEvents();

        // Display events in TextViews
        displayEventInTextView(textView1, events, 0);
        displayEventInTextView(textView2, events, 1);
        displayEventInTextView(textView3, events, 2);
        displayEventInTextView(textView4, events, 3);
        displayEventInTextView(textView5, events, 4);
        displayEventInTextView(textView6, events, 5);
        displayEventInTextView(textView7, events, 6);
        displayEventInTextView(textView8, events, 7);

        return view;
    }

    // Helper method to display an event in a TextView
    private void displayEventInTextView(TextView textView, List<eventClass> events, int index) {
        if (index < events.size()) {
            eventClass event = events.get(index);
            // Customize this line to display the event information as needed
            textView.setText("Event: "+ event.getName() + "\nDate: " + event.getDateAndTime() +
                    "\nLocation: "  + event.getLocation());
        } else {
            // If the index is beyond the number of events, clear the TextView
            textView.setText("");
        }
    }
}


