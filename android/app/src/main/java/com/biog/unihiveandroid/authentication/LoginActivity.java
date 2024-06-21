package com.biog.unihiveandroid.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.dashboard.admin.AdminDashboardActivity;
import com.biog.unihiveandroid.dashboard.superadmin.SuperAdminDashboardActivity;
import com.biog.unihiveandroid.model.RegisterRequest;
import com.biog.unihiveandroid.service.AuthenticationService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Base64;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailAddressLoginText;
    private EditText passwordLoginText;
    private AuthenticationService authenticationService;
    private SharedPreferences sharedPreferences;

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
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
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
        authenticationService.login(loginRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JsonObject jsonObject = new Gson().fromJson(responseString, JsonObject.class);
                        String token = jsonObject.get("token").getAsString();
                        decodeJWTAndHandleRole(token);
                        Log.d("unihivehome", "Login successful: " + token);
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        Log.d("unihivehome", "Login failed: " + exception.getMessage());
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Email or password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("unihivehome", "Login failed: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToken(String token, String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(role, token);
        editor.apply();
        editor.commit();
    }

    private void decodeJWTAndHandleRole(String token) {
        try {
            String secretKey = BuildConfig.API_KEY_NAME;
            String[] chunks = token.split("\\.");
            String header = new String(Base64.getUrlDecoder().decode(chunks[0]));
            String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
            byte[] decodedBytes = Base64.getDecoder().decode(secretKey);
            SecretKey secretKeySpec = Keys.hmacShaKeyFor(decodedBytes);
            // Parse the token
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKeySpec)
                    .build()
                    .parseSignedClaims(token);

            // Extract user role from claims
            String role = claims.getPayload().get("role", String.class);

            // Handle user role as needed
            if ("SUPER_ADMIN".equals(role)) {
                saveToken(token, "superadmin");
                startActivity(new Intent(LoginActivity.this, SuperAdminDashboardActivity.class));
            } else if ("ADMIN".equals(role)) {
                saveToken(token, "admin");
                startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
            } else if ("STUDENT".equals(role)) {
                saveToken(token, "student");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to decode JWT", Toast.LENGTH_SHORT).show();
        }
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
