package com.example.jadeinsure.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jadeinsure.R;
import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {

    private RecyclerView policyRecyclerView;
    private MaterialCardView quickActionCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews(view);
        setupQuickActions();
        loadUserPolicies();

        return view;
    }

    private void initializeViews(View view) {
        policyRecyclerView = view.findViewById(R.id.policy_recycler_view);
        quickActionCard = view.findViewById(R.id.quick_action_card);
    }

    private void setupQuickActions() {
        // Setup quick action buttons
    }

    private void loadUserPolicies() {
        // Load and display user policies
    }
}