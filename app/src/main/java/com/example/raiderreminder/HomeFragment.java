package com.example.raiderreminder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView textViewHomeDate;
    private LinearLayout linearContainer;  // LinearLayout to hold dynamically created TextViews
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateDateTime();
            handler.postDelayed(this, 1000); // Update every 1 second (1000 milliseconds)
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the TextView for displaying today's date
        textViewHomeDate = view.findViewById(R.id.textViewHomeDate);

        // Find the LinearLayout for holding dynamically created TextViews
        linearContainer = view.findViewById(R.id.linearContainer);

        FloatingActionButton floatingSettingButton = view.findViewById(R.id.floatingSettingButton);
        floatingSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with SettingsFragment
                replaceFragment(new SettingsFragment());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Start updating date and time when the fragment is resumed
        handler.post(updateTimeRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop updating date and time when the fragment is paused
        handler.removeCallbacks(updateTimeRunnable);
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.addToBackStack(null);  // Optional: Add to back stack
            fragmentTransaction.commit();
        }
    }

    private void updateDateTime() {
        // Get the current date and time
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy hh:mm a");
        String formattedDate = dateFormat.format(currentDate);

        // Set the formatted date and time to the TextView
        textViewHomeDate.setText(formattedDate);

        // Remove events that have passed
        removePassedEvents(currentDate);

        // Display today's events dynamically
        displayTodayEvents();
    }

    private void removePassedEvents(Date currentDate) {
        List<eventClass> events = EventManager.getEvents();

        for (eventClass event : new ArrayList<>(events)) {
            // Compare the event's time with the current time
            if (event.getTask().getTime().before(currentDate)) {
                EventManager.removeEventFromList(event.getName());
            }
        }
    }

    private void displayTodayEvents() {
        // Get today's date
        LocalDate today = LocalDate.now();

        // Get events for today
        List<eventClass> todayEvents = EventManager.getEventsForDay(today);

        // Clear the existing TextViews in the LinearLayout
        linearContainer.removeAllViews();

        if (todayEvents.isEmpty()) {
            // If no events for today, display a message
            TextView noEventsTextView = new TextView(getContext());
            noEventsTextView.setText("No events today :(");
            noEventsTextView.setGravity(Gravity.CENTER);
            linearContainer.addView(noEventsTextView);
        } else {
            // Display events in dynamically created TextViews
            for (eventClass event : todayEvents) {
                TextView eventTextView = createEventTextView(event);
                linearContainer.addView(eventTextView);
            }
        }
    }


    private TextView createEventTextView(eventClass event) {
        TextView eventTextView = new TextView(getContext());
        // Customize this line to format the event information as needed
        String eventText = "Event: "+ event.getName() + "\nDate: " + event.getDateAndTime() +
                "\nLocation: "  + event.getLocation();
        eventTextView.setText(eventText);
        return eventTextView;
    }
}
