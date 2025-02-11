package com.example.jadeinsure.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.jadeinsure.R;
import com.example.jadeinsure.network.AuthResponse;
import com.example.jadeinsure.network.RetrofitClient;
import com.example.jadeinsure.network.SignupRequest;
import com.example.jadeinsure.network.SessionManager;
import com.example.jadeinsure.ui.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.ResponseBody;
import java.net.UnknownHostException;
import java.net.SocketTimeoutException;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout nameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout confirmPasswordLayout;
    private TextInputEditText nameInput;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText confirmPasswordInput;
    private MaterialButton signupButton;
    private CircularProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        nameLayout = findViewById(R.id.name_layout);
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);
        confirmPasswordLayout = findViewById(R.id.confirm_password_layout);
        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        signupButton = findViewById(R.id.signup_button);
        progressIndicator = findViewById(R.id.progress_indicator);
    }

    private void setupListeners() {
        signupButton.setOnClickListener(v -> attemptSignup());
    }

    private void attemptSignup() {
        // Reset errors
        nameLayout.setError(null);
        emailLayout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (!validateInput(name, email, password, confirmPassword)) {
            return;
        }

        showLoading(true);

        SignupRequest request = new SignupRequest(name, email, password);
        RetrofitClient.getInstance().getApiService().signup(request)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        showLoading(false);
                        if (response.isSuccessful() && response.body() != null) {
                            handleSuccessfulSignup(response.body());
                        } else {
                            Log.e("SignupError", "Failed with code: " + response.code());
                            handleSignupError(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        showLoading(false);
                        handleNetworkError(t);
                    }
                });
    }

    private boolean validateInput(String name, String email, String password, String confirmPassword) {
        boolean isValid = true;

        if (name.isEmpty()) {
            nameLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (email.isEmpty()) {
            emailLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError(getString(R.string.error_invalid_email));
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        } else if (password.length() < 6) {
            passwordLayout.setError(getString(R.string.error_password_too_short));
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordLayout.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError(getString(R.string.error_passwords_dont_match));
            isValid = false;
        }

        return isValid;
    }

    private void handleSuccessfulSignup(AuthResponse response) {
        if (response.getToken() != null) {
            // Save the auth token
            SessionManager.getInstance().saveToken(response.getToken());
            
            // Show success message and finish
            Toast.makeText(this, R.string.signup_success, Toast.LENGTH_SHORT).show();
            
            // Start MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, R.string.error_signup_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSignupError(ResponseBody errorBody) {
        try {
            if (errorBody != null) {
                String errorResponse = errorBody.string();
                Log.e("SignupError", "Error response: " + errorResponse);
                
                try {
                    // Try to parse as JSON
                    JSONObject errorJson = new JSONObject(errorResponse);
                    String message = errorJson.optString("message", getString(R.string.error_signup_failed));
                    String error = errorJson.optString("error", "");

                    if (error.contains("email")) {
                        emailLayout.setError(getString(R.string.error_email_exists));
                    } else if (error.contains("password")) {
                        passwordLayout.setError(message);
                    } else if (error.contains("name")) {
                        nameLayout.setError(message);
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // If response is not JSON, log it and show generic error
                    Log.e("SignupError", "Non-JSON error response: " + errorResponse);
                    Toast.makeText(this, R.string.error_network, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("SignupError", "Error parsing response", e);
            Toast.makeText(this, R.string.error_signup_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNetworkError(Throwable t) {
        Log.e("SignupError", "Network error", t);
        if (t instanceof UnknownHostException) {
            Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_LONG).show();
        } else if (t instanceof SocketTimeoutException) {
            Toast.makeText(this, R.string.error_timeout, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.error_network, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoading(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        signupButton.setEnabled(!show);
    }
}