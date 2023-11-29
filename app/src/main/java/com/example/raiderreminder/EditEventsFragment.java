package com.example.raiderreminder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


public class EditEventsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_editevents, container, false);

        //Create the home tool bar
        Toolbar editeventsToolBar = view.findViewById(R.id.editeventstoolbar);

        // Ensure that the parent activity is an AppCompatActivity
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.setSupportActionBar(editeventsToolBar);

            // Set the title of the toolbar
            appCompatActivity.getSupportActionBar().setTitle("Edit Events");
        }


        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu resource
        inflater.inflate(R.menu.editeventstoolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}