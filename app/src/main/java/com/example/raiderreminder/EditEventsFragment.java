package com.example.raiderreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Iterator;
import java.util.List;

public class EditEventsFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            // Get the list of events from the EventManager
            List<eventClass> allEvents = EventManager.getEvents();

            // Iterate through the events and remove the checked ones
            for (Iterator<eventClass> iterator = allEvents.iterator(); iterator.hasNext(); ) {
                eventClass event = iterator.next();
                if (event.isChecked()) {
                    event.cancelAlarms(requireContext());
                    iterator.remove();
                }
            }

            // Notify the adapter that the dataset has changed
            eventAdapter.notifyDataSetChanged();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_editevents, container, false);

        // Set up the toolbar
        Toolbar eeToolbar = view.findViewById(R.id.editeventstoolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(eeToolbar);

        // Set the title of the toolbar
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Edit Events");

        // Set Has Options Menu to true
        setHasOptionsMenu(true);

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.eventsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get the list of events from the EventManager
        List<eventClass> allEvents = EventManager.getEvents();

        // Create and set the adapter
        eventAdapter = new EventAdapter(allEvents);
        recyclerView.setAdapter(eventAdapter);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu resource
        inflater.inflate(R.menu.editeventstoolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
