package com.example.jadeinsure.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.jadeinsure.R;
import com.example.jadeinsure.network.AuthResponse;
import com.example.jadeinsure.network.LoginRequest;
import com.example.jadeinsure.network.RetrofitClient;
import com.example.jadeinsure.network.SessionManager;
import com.example.jadeinsure.ui.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private MaterialButton loginButton;
    private CircularProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is already logged in
        if (SessionManager.getInstance().isLoggedIn()) {
            startMainActivity();
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        progressIndicator = findViewById(R.id.progress_indicator);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(v -> attemptLogin());
        findViewById(R.id.signup_link).setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Reset errors
        emailLayout.setError(null);
        passwordLayout.setError(null);

        // Validate input
        if (email.isEmpty()) {
            emailLayout.setError(getString(R.string.error_field_required));
            return;
        }

        if (password.isEmpty()) {
            passwordLayout.setError(getString(R.string.error_field_required));
            return;
        }

        showLoading(true);

        LoginRequest loginRequest = new LoginRequest(email, password);
        RetrofitClient.getInstance().getApiService().login(loginRequest)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        showLoading(false);
                        if (response.isSuccessful() && response.body() != null) {
                            handleSuccessfulLogin(response.body());
                        } else {
                            handleLoginError();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        showLoading(false);
                        handleLoginError();
                    }
                });
    }

    private void handleSuccessfulLogin(AuthResponse response) {
        SessionManager.getInstance().saveToken(response.getToken());
        startMainActivity();
        finish();
    }

    private void handleLoginError() {
        Toast.makeText(this, R.string.error_login_failed, Toast.LENGTH_SHORT).show();
    }

    private void showLoading(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        loginButton.setEnabled(!show);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}