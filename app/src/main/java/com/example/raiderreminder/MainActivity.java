package com.example.raiderreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

import com.example.raiderreminder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home){
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.calender) {
                replaceFragment(new CalenderFragment());
            }else if (itemId == R.id.notifications) {
                replaceFragment(new NotificationFragment());
            }else if (itemId == R.id.settings) {
                replaceFragment(new SettingsFragment());
            }

            return true;
        });

        binding.floatingActionButton.setOnClickListener(view -> {
            // Handle the FAB click event
            replaceFragment(new AddFragment());
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}