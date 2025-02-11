package com.example.jadeinsure.ui.services;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.jadeinsure.R;
import com.example.jadeinsure.model.Service;
import com.example.jadeinsure.network.RetrofitClient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SERVICE_ID = "service_id";

    private ImageView serviceIcon;
    private TextView serviceName;
    private TextView serviceDescription;
    private TextView serviceCategory;
    private MaterialButton actionButton;
    private CircularProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        String serviceId = getIntent().getStringExtra(EXTRA_SERVICE_ID);
        if (serviceId == null) {
            finish();
            return;
        }

        initializeViews();
        loadServiceDetails(serviceId);
    }

    private void initializeViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        serviceIcon = findViewById(R.id.service_icon);
        serviceName = findViewById(R.id.service_name);
        serviceDescription = findViewById(R.id.service_description);
        serviceCategory = findViewById(R.id.service_category);
        actionButton = findViewById(R.id.action_button);
        progressIndicator = findViewById(R.id.progress_indicator);
    }

    private void loadServiceDetails(String serviceId) {
        showLoading(true);

        RetrofitClient.getInstance().getApiService().getServiceDetails(serviceId)
                .enqueue(new Callback<Service>() {
                    @Override
                    public void onResponse(Call<Service> call, Response<Service> response) {
                        showLoading(false);
                        if (response.isSuccessful() && response.body() != null) {
                            displayServiceDetails(response.body());
                        } else {
                            handleError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Service> call, Throwable t) {
                        showLoading(false);
                        handleError();
                    }
                });
    }

    private void displayServiceDetails(Service service) {
        serviceName.setText(service.getName());
        serviceDescription.setText(service.getDescription());
        serviceCategory.setText(service.getCategory());

        Glide.with(this)
                .load(service.getIconUrl())
                .placeholder(R.drawable.ic_service_placeholder)
                .error(R.drawable.ic_service_error)
                .into(serviceIcon);

        actionButton.setOnClickListener(v -> {
            // TODO: Handle service action (e.g., start application process)
        });
    }

    private void showLoading(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        actionButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void handleError() {
        Toast.makeText(this, R.string.error_loading_service_details, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}