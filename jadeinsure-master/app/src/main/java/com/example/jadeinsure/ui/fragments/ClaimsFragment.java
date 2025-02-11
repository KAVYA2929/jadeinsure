package com.example.jadeinsure.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jadeinsure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClaimsFragment extends Fragment {

    private RecyclerView claimsRecyclerView;
    private FloatingActionButton newClaimFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_claims, container, false);

        initializeViews(view);
        setupListeners();

        return view;
    }

    private void initializeViews(View view) {
        claimsRecyclerView = view.findViewById(R.id.claims_recycler_view);
        claimsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newClaimFab = view.findViewById(R.id.new_claim_fab);
    }

    private void setupListeners() {
        newClaimFab.setOnClickListener(v -> {
            // TODO: Navigate to new claim form
            showNewClaimDialog();
        });
    }

    private void showNewClaimDialog() {
        // TODO: Implement new claim dialog or navigate to claim form
    }
}