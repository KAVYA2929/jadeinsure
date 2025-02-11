package com.example.jadeinsure.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jadeinsure.R;
import com.example.jadeinsure.model.Service;
import com.example.jadeinsure.network.RetrofitClient;
import com.example.jadeinsure.ui.adapters.ServicesAdapter;
import com.example.jadeinsure.ui.services.ServiceDetailsActivity;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends Fragment {

    private RecyclerView servicesRecyclerView;
    private ServicesAdapter servicesAdapter;
    private List<Service> servicesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        servicesRecyclerView = view.findViewById(R.id.services_recycler_view);
        servicesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        setupAdapter();
        loadServices();

        return view;
    }

    private void setupAdapter() {
        servicesAdapter = new ServicesAdapter(servicesList, service -> {
            // Handle service click
            navigateToServiceDetails(service);
        });
        servicesRecyclerView.setAdapter(servicesAdapter);
    }

    private void loadServices() {
        RetrofitClient.getInstance().getApiService().getServices()
                .enqueue(new Callback<List<Service>>() {
                    @Override
                    public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            servicesList.clear();
                            servicesList.addAll(response.body());
                            servicesAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Service>> call, Throwable t) {
                        // Handle error
                        Toast.makeText(getContext(), R.string.error_loading_services, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToServiceDetails(Service service) {
        Intent intent = new Intent(getContext(), ServiceDetailsActivity.class);
        intent.putExtra(ServiceDetailsActivity.EXTRA_SERVICE_ID, service.getId());
        startActivity(intent);
    }
}