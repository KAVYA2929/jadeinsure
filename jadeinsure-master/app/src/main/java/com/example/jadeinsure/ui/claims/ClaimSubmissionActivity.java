package com.example.jadeinsure.ui.claims;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.jadeinsure.R;
import com.example.jadeinsure.model.Claim;
import com.example.jadeinsure.model.Policy;
import com.example.jadeinsure.model.ClaimRequest;
import com.example.jadeinsure.network.RetrofitClient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Locale;
public class ClaimSubmissionActivity extends AppCompatActivity {

    private MaterialAutoCompleteTextView policySpinner;
    private TextInputLayout policyLayout;
    private TextInputLayout descriptionLayout;
    private TextInputLayout amountLayout;
    private TextInputLayout dateLayout;
    private TextInputEditText descriptionInput;
    private TextInputEditText amountInput;
    private TextInputEditText dateInput;
    private MaterialButton submitButton;
    private LinearProgressIndicator progressIndicator;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    private Date selectedDate;
    private List<Policy> userPolicies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_submission);

        initializeViews();
        setupListeners();
        loadUserPolicies();
    }

    private void initializeViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        policySpinner = findViewById(R.id.policy_spinner);
        policyLayout = findViewById(R.id.policy_layout);
        descriptionLayout = findViewById(R.id.description_layout);
        amountLayout = findViewById(R.id.amount_layout);
        dateLayout = findViewById(R.id.date_layout);
        descriptionInput = findViewById(R.id.description_input);
        amountInput = findViewById(R.id.amount_input);
        dateInput = findViewById(R.id.date_input);
        submitButton = findViewById(R.id.submit_button);
        progressIndicator = findViewById(R.id.progress_indicator);
    }

    private void setupListeners() {
        dateInput.setOnClickListener(v -> showDatePicker());
        submitButton.setOnClickListener(v -> validateAndSubmitClaim());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (selectedDate != null) {
            calendar.setTime(selectedDate);
        }

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    selectedDate = calendar.getTime();
                    dateInput.setText(dateFormat.format(selectedDate));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void loadUserPolicies() {
        showLoading(true);
        RetrofitClient.getInstance().getApiService().getUserPolicies()
                .enqueue(new Callback<List<Policy>>() {
                    @Override
                    public void onResponse(Call<List<Policy>> call, Response<List<Policy>> response) {
                        showLoading(false);
                        if (response.isSuccessful() && response.body() != null) {
                            userPolicies = response.body();
                            setupPolicySpinner();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Policy>> call, Throwable t) {
                        showLoading(false);
                        Toast.makeText(ClaimSubmissionActivity.this,
                                R.string.error_loading_policies, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupPolicySpinner() {
        ArrayAdapter<Policy> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, userPolicies);
        policySpinner.setAdapter(adapter);
    }

    private void validateAndSubmitClaim() {
        // Reset errors
        policyLayout.setError(null);
        descriptionLayout.setError(null);
        amountLayout.setError(null);
        dateLayout.setError(null);

        Policy selectedPolicy = (Policy) policySpinner.getTag();
        String description = descriptionInput.getText().toString().trim();
        String amountStr = amountInput.getText().toString().trim();

        if (selectedPolicy == null) {
            policyLayout.setError(getString(R.string.error_select_policy));
            return;
        }

        if (description.isEmpty()) {
            descriptionLayout.setError(getString(R.string.error_field_required));
            return;
        }

        if (amountStr.isEmpty()) {
            amountLayout.setError(getString(R.string.error_field_required));
            return;
        }

        if (selectedDate == null) {
            dateLayout.setError(getString(R.string.error_select_date));
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            amountLayout.setError(getString(R.string.error_invalid_amount));
            return;
        }

        submitClaim(selectedPolicy.getId(), description, amount, selectedDate);
    }

    private void submitClaim(String policyId, String description, double amount, Date incidentDate) {
        showLoading(true);

        String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(incidentDate);
        ClaimRequest request = new ClaimRequest(
            policyId,
            description,
            amount,
            formattedDate,
            "GENERAL" // or whatever default claim type you want to use
        );
        RetrofitClient.getInstance().getApiService().submitClaim(request)
                .enqueue(new Callback<Claim>() {
                    @Override
                    public void onResponse(Call<Claim> call, Response<Claim> response) {
                        showLoading(false);
                        if (response.isSuccessful()) {
                            Toast.makeText(ClaimSubmissionActivity.this,
                                    R.string.claim_submitted_successfully, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            handleSubmissionError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Claim> call, Throwable t) {
                        showLoading(false);
                        handleSubmissionError();
                    }
                });
    }

    private void handleSubmissionError() {
        Toast.makeText(this, R.string.error_submitting_claim, Toast.LENGTH_SHORT).show();
    }

    private void showLoading(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        submitButton.setEnabled(!show);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}