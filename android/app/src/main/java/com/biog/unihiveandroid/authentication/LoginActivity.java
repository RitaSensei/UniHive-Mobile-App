package com.biog.unihiveandroid.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.SettingsActivity;
import com.biog.unihiveandroid.model.RegisterRequest;
import com.biog.unihiveandroid.service.AuthenticationService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.ui.home.FragmentHome;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailAddressLoginText;
    private EditText passwordLoginText;
    private AuthenticationService authenticationService;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initializeViews();
        TextView forgotPasswordText = findViewById(R.id.forgetPasswordTextView);
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    public void onCreateAccountBtnClick(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    public void onLoginBtnPageLoginClick(View view) {
        String email = emailAddressLoginText.getText().toString().trim();
        String password = passwordLoginText.getText().toString().trim();

        if (email.isEmpty() && password.isEmpty()) {
            Snackbar.make(view, "Email and Password required!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .show();
            return;
        } else if (email.isEmpty()) {
            Snackbar.make(view, "Email required!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .show();
            return;
        } else if (password.isEmpty()) {
            Snackbar.make(view, "Password required!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .show();
            return;
        }
        if (!validateEmail(email)) {
            emailAddressLoginText.setError("Please enter a valid email address!");
            return;
        }
        RegisterRequest loginRequest = new RegisterRequest(email, password);
        authenticationService.login(loginRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish(); // Finish LoginActivity to prevent navigating back to it using the back button
                } else {
                    Toast.makeText(LoginActivity.this, "Email or password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getCause(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateEmail(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    private void initializeViews() {
        emailAddressLoginText = findViewById(R.id.editEmailAddressLoginText);
        passwordLoginText = findViewById(R.id.editPasswordLoginText);
        RetrofitService retrofitService = new RetrofitService();
        authenticationService = retrofitService.getRetrofit().create(AuthenticationService.class);
    }
}
