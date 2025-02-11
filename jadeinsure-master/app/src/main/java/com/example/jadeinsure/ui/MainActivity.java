package com.example.jadeinsure.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.jadeinsure.ui.fragments.ClaimsFragment;
import com.example.jadeinsure.ui.fragments.HomeFragment;
import com.example.jadeinsure.ui.fragments.ProfileFragment;
import com.example.jadeinsure.ui.fragments.ServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.jadeinsure.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();

        // Start with Home fragment
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            loadFragment(item);
            return true;
        });
    }

    private void loadFragment(MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (itemId == R.id.nav_services) {
            fragment = new ServicesFragment();
        } else if (itemId == R.id.nav_claims) {
            fragment = new ClaimsFragment();
        } else if (itemId == R.id.nav_profile) {
            fragment = new ProfileFragment();
        }

        if (fragment != null) {
            replaceFragment(fragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}