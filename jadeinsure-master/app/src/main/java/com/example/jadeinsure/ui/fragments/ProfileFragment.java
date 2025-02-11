package com.example.jadeinsure.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.jadeinsure.R;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {

    private TextView userNameText;
    private TextView emailText;
    private MaterialButton editProfileButton;
    private MaterialButton logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeViews(view);
        setupListeners();
        loadUserProfile();

        return view;
    }

    private void initializeViews(View view) {
        userNameText = view.findViewById(R.id.user_name);
        emailText = view.findViewById(R.id.user_email);
        editProfileButton = view.findViewById(R.id.edit_profile_button);
        logoutButton = view.findViewById(R.id.logout_button);
    }

    private void setupListeners() {
        editProfileButton.setOnClickListener(v -> {
            // TODO: Navigate to edit profile screen
        });

        logoutButton.setOnClickListener(v -> {
            // TODO: Implement logout functionality
            handleLogout();
        });
    }

    private void loadUserProfile() {
        // TODO: Load user profile from SharedPreferences or API
    }

    private void handleLogout() {
        // TODO: Clear user session and navigate to login screen
    }
}