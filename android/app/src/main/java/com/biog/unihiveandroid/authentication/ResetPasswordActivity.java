package com.biog.unihiveandroid.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.RegisterRequest;
import com.biog.unihiveandroid.service.AuthenticationService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText emailAddressResetText;
    private AuthenticationService authenticationService;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initializeViews();
    }

    public void onResetPasswordBtnClick(View view) {
        String email = emailAddressResetText.getText().toString().trim();

        if (email.isEmpty()) {
            Snackbar.make(view, "Email required!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .show();
            return;
        }
        if (!validateEmail(email)) {
            emailAddressResetText.setError("Please enter a valid email address!");
            return;
        }
        authenticationService.forgottenPassword(new RegisterRequest(email)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String responseString = response.body().string();
                    JsonObject jsonObject = new Gson().fromJson(responseString, JsonObject.class);
                    String token = jsonObject.get("token").getAsString();
                    if(Objects.equals(token, "INVALID_TOKEN")) {
                        Snackbar.make(view, "Email not found!", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                                .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                                .show();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Email sent!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        finish();
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                    Toast.makeText(ResetPasswordActivity.this, "Error sending email ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Error sending email ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateEmail(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    private void initializeViews() {
        emailAddressResetText = findViewById(R.id.editEmailAddressResetText);
        RetrofitService retrofitService = new RetrofitService();
        authenticationService = retrofitService.getRetrofit().create(AuthenticationService.class);
    }
}
