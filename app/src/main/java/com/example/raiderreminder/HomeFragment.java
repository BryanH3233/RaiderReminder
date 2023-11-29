package com.example.raiderreminder;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView todayEventsTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Create the home tool bar
        Toolbar homeToolBar = view.findViewById(R.id.hometoolbar);
        todayEventsTextView = view.findViewById(R.id.textView1);


        // Ensure that the parent activity is an AppCompatActivity
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.setSupportActionBar(homeToolBar);

            // Set the title of the toolbar
            appCompatActivity.getSupportActionBar().setTitle("Today's Events");

            // Set a subtitle with the current date and time, and update it every second
            Handler handler = new Handler();
            Runnable updateDateTimeRunnable = new Runnable() {
                @Override
                public void run() {
                    appCompatActivity.getSupportActionBar().setSubtitle(DateFormat.format("MMMM dd, yyyy h:mm a", Calendar.getInstance()));
                    handler.postDelayed(this, 1000); // Update every second
                }
            };

            handler.post(updateDateTimeRunnable);


            // Remove expired events
            EventManager.removeExpiredEvents();


            // Display today's events
            displayTodayEvents();
        }

        return view;
    }

    private void updateDateTime(androidx.appcompat.app.ActionBar actionBar) {
        // Get the current date and time
        Date currentDate = Calendar.getInstance().getTime();

        // Format the date and time
        String formattedDateTime = DateFormat.format("EEEE, MMM dd, yyyy hh:mm a", currentDate).toString();

        // Set the formatted date and time to the ActionBar title
        if (actionBar != null) {
            actionBar.setTitle(formattedDateTime);
        }
    }

    private void displayTodayEvents() {
        // Get the list of events from the EventManager
        List<eventClass> allEvents = EventManager.getEvents();

        // Get today's date
        Calendar todayCalendar = Calendar.getInstance();
        int todayYear = todayCalendar.get(Calendar.YEAR);
        int todayMonth = todayCalendar.get(Calendar.MONTH) + 1; // Months are 0-based
        int todayDay = todayCalendar.get(Calendar.DAY_OF_MONTH);

        // Filter events for today
        StringBuilder todayEventsStringBuilder = new StringBuilder("Today's Events:\n\n");

        // Check if there are events for today
        boolean hasEventsToday = false;

        for (eventClass event : allEvents) {
            if (event.getYear() == todayYear && event.getMonth() == todayMonth && event.getDay() == todayDay) {
                // Append the event information to the StringBuilder
                todayEventsStringBuilder.append("Event: ")
                        .append(event.getName())
                        .append("\nTime: ")
                        .append(event.getTime12Hour())
                        .append("\nLocation: ")
                        .append(event.getLocation())
                        .append("\n\n\n");

                hasEventsToday = true;
            }
        }

        // Check if there are no events for today
        if (!hasEventsToday) {
            todayEventsStringBuilder.append("No events today!");
        }
        // Set the text of the TextView with today's events
        todayEventsTextView.setText(todayEventsStringBuilder.toString());
    }
}
