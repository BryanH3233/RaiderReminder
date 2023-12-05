package com.example.raiderreminder;


import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;



import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import android.widget.Button;


public class CalenderFragment extends Fragment implements CalenderAdapter.OnItemListener {

    // View binding for the layout elements
    private TextView monthYearText;
    private RecyclerView calenderRecyclerView;
    private LocalDate selectedDate;



    // Called when the fragment is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        // Set click listeners for next and previous month buttons
        Button nextMonthButton = view.findViewById(R.id.nextMonthButton);
        nextMonthButton.setOnClickListener(v -> nextMonthAction(v));

        Button prevMonthButton = view.findViewById(R.id.prevMonthButton);
        prevMonthButton.setOnClickListener(v -> previousMonthAction(v));

        // Initialize widgets and set default date
        initWidgets(view);
        selectedDate = LocalDate.now();
        setMonthView();

        // Return the view for the fragment
        return view;
    }

    // Set up the month view with the current selected date
    private void setMonthView() {
        // Update the text displaying the month and year
        monthYearText.setText(monthYearFromDate(selectedDate));

        // Get the days of the month and display them in the RecyclerView
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        CalenderAdapter calenderAdapter = new CalenderAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calenderRecyclerView.setLayoutManager(layoutManager);
        calenderRecyclerView.setAdapter(calenderAdapter);
    }

    // Generate an array of strings representing the days of the month
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                int dayOfMonth = i - dayOfWeek;
                String dayString = String.valueOf(dayOfMonth);

                // Check if there are events for the current day and add a dot
                if (EventManager.getEventsForDay(selectedDate.withDayOfMonth(dayOfMonth)).size() > 0) {
                    dayString += "★";
                }

                daysInMonthArray.add(dayString);
            }
        }
        return daysInMonthArray;
    }



    // Handle the action when the "Previous Month" button is clicked
    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    // Handle the action when the "Next Month" button is clicked
    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    // Format the selected date to display the month and year
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    // Initialize widgets by finding views in the layout
    private void initWidgets(View view) {
        calenderRecyclerView = view.findViewById(R.id.calenderRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }



    // Handle the item click event in the RecyclerView
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            // Extract the numeric part from dayText
            String numericDay = dayText.replaceAll("[^\\d]", "");

            if (!numericDay.isEmpty()) {
                // Parse the numeric day
                int dayPart = Integer.parseInt(numericDay);

                // Get events for the current day
                ArrayList<eventClass> eventsForDay = EventManager.getEventsForDay(selectedDate.withDayOfMonth(dayPart));

                // Create a StringBuilder to build the event details
                StringBuilder eventDetails = new StringBuilder();
                for (eventClass event : eventsForDay) {
                    eventDetails.append(event.getName()).append("\n").append(event.getDateAndTime()).append("\n\n");
                }

                // Show a dialog with the selected date and events
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Selected Date: " + dayPart + " " + monthYearFromDate(selectedDate))
                        .setMessage(eventDetails.toString())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        }
    }

}

