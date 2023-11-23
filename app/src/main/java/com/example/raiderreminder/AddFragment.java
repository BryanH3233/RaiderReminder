package com.example.raiderreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class AddFragment extends Fragment {

    private TextInputEditText inputNameEditText, inputDayEditText, inputMonthEditText,
            inputYearEditText, inputHourEditText, inputMinuteEditText, inputLocationEditText;
    private Button bttnDone;
    EventManager eventsList = new EventManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Find your UI elements by ID
        inputNameEditText = view.findViewById(R.id.inputName);
        inputDayEditText = view.findViewById(R.id.inputDay);
        inputMonthEditText = view.findViewById(R.id.inputMonth);
        inputYearEditText = view.findViewById(R.id.inputYear);
        inputHourEditText = view.findViewById(R.id.inputHour);
        inputMinuteEditText = view.findViewById(R.id.inputMinute);
        inputLocationEditText = view.findViewById(R.id.inputLocation);
        bttnDone = view.findViewById(R.id.bttnDone);

        // Set an onClickListener for the button
        bttnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });

        return view;
    }

    public void createEvent() {
        eventClass event = new eventClass();

        // Retrieve the values from the EditText fields
        String eventName = inputNameEditText.getText().toString();

        // Parse integer values for day, month, year, hour, and minute
        int eventDay, eventMonth, eventYear, eventHour, eventMinute;
        try {
            eventDay = Integer.parseInt(inputDayEditText.getText().toString());
            eventMonth = Integer.parseInt(inputMonthEditText.getText().toString());
            eventYear = Integer.parseInt(inputYearEditText.getText().toString());
            eventHour = Integer.parseInt(inputHourEditText.getText().toString());
            eventMinute = Integer.parseInt(inputMinuteEditText.getText().toString());
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            return;
        }

        String eventLocation = inputLocationEditText.getText().toString();

        // Use the entered values as needed
        event.setName(eventName);
        event.setDay(eventDay);
        event.setMonth(eventMonth);
        event.setYear(eventYear);
        event.setHour(eventHour);
        event.setMinute(eventMinute);
        event.setLocation(eventLocation);

        EventManager.addEventToList(event);
    }
}
