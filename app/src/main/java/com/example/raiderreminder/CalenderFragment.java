package com.example.raiderreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raiderreminder.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import android.widget.Button;

import androidx.fragment.app.Fragment;
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
                daysInMonthArray.add(String.valueOf(dayOfMonth));

                // Get events for the current day
                ArrayList<eventClass> eventsForDay = EventManager.getEventsForDay(selectedDate.withDayOfMonth(dayOfMonth));
                for (eventClass event : eventsForDay) {
                    // Format the event information and add to the day's text
                    String eventInfo = event.getName() + "\n" + event.getDateAndTime();
                    daysInMonthArray.set(i - 1, daysInMonthArray.get(i - 1) + "\n" + eventInfo);
                }
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
    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            // Show a toast message with the selected date and events
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            ArrayList<eventClass> eventsForDay = EventManager.getEventsForDay(selectedDate);
            for (eventClass event : eventsForDay) {
                message += "\n" + event.getName() + "\n" + event.getDateAndTime();
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

}