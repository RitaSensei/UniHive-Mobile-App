package com.biog.unihiveandroid.dashboard.superadmin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.model.PasswordUpdateRequest;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.SuperAdminService;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {
    private Context mContext;
    private EditText superadminEmailEditText, superadminOldPasswordEditText, superadminNewPasswordEditText;
    private Button superadminUpdateButton;
    private SharedPreferences sharedPreferences;
    private SuperAdminService superadminService;
    private String initialToken, superadminEmail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_superadmin_profile, container, false);
        mContext = requireContext();
        TextView fragmentAdminsTitle = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_title);
        fragmentAdminsTitle.setText(R.string.ic_profile);
        ImageButton searchIcon = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_search_icon);
        searchIcon.setVisibility(View.GONE);

        initializeViews();
        superadminEmailEditText = rootView.findViewById(R.id.edit_email_address_superadmin_profile);
        superadminOldPasswordEditText = rootView.findViewById(R.id.edit_old_password_superadmin_profile);
        superadminNewPasswordEditText = rootView.findViewById(R.id.edit_new_password_superadmin_profile);
        superadminUpdateButton = rootView.findViewById(R.id.update_superadmin_profile_button);
        superadminEmailEditText.setText(superadminEmail);
//        superadminNewPasswordEditText.setEnabled(false);

        superadminUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateProfileClick(view);
            }
        });
        return rootView;
    }

    private void onUpdateProfileClick(View view) {
        String email = superadminEmailEditText.getText().toString();
        String oldPassword = superadminOldPasswordEditText.getText().toString();
        String newPassword = superadminNewPasswordEditText.getText().toString();

        if (email.isEmpty()) {
            superadminUpdateButton.setEnabled(false);
            return;
        } else {
            superadminUpdateButton.setEnabled(true);
            if (!validateEmail(email)) {
                Snackbar.make(view, "Invalid email address!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                        .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                        .show();
                return;
            }
        }
        superadminNewPasswordEditText.setEnabled(!oldPassword.isEmpty());
        if (!oldPassword.isEmpty()) {
            if (!validatePassword(oldPassword)) {
                superadminOldPasswordEditText.setError("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character!");
                return;
            }
            if (!validatePassword(newPassword)) {
                superadminNewPasswordEditText.setError("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character!");
                return;
            }
            if (superadminOldPasswordEditText.equals(superadminNewPasswordEditText)) {
                Snackbar.make(view, "New Password must be different", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                        .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                        .show();
                return;
            }
        }

        if (!superadminEmailEditText.getText().toString().equals(superadminEmail)) {
            superadminService.updateSuperAdminEmail("Bearer " + initialToken, email).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    Log.d("fragmentProfile", "initial token in email : "+ initialToken);
                    Log.d("fragmentProfile", "response in email : "+ response);
                    if (response.isSuccessful()) {
                        try {
                            removeToken();
                            String responseString = response.body().string();
                            Log.d("fragmentProfile", "responseString : "+ responseString);
                            JsonObject jsonObject = new Gson().fromJson(responseString, JsonObject.class);
                            String token = jsonObject.get("token").getAsString();
                            decodeJWT(token);
                            Log.d("fragmentProfile", "new token : "+ token);
                            handleRole(token);
                            Snackbar.make(view, "Email updated successfully", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(getResources().getColor(R.color.green, null))
                                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                                    .show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("fragmentProfile", "email updating failed: " + e.getMessage());
                            Toast.makeText(mContext, "error in updating profile", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.d("fragmentProfile", "email update failed first: " + response.message());
                        Snackbar.make(view, "Error updating email", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                                .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                                .show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.d("fragmentProfile", "email update failed second: " + t.getMessage());
                    Snackbar.make(view, "Error updating email", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                            .show();
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    superadminEmailEditText.getText().clear();
                    superadminOldPasswordEditText.getText().clear();
                    superadminNewPasswordEditText.getText().clear();
                }
            }, 2000);
        }

        if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
            PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest(oldPassword, newPassword);
            superadminService.updateSuperAdminPassword("Bearer " + initialToken, passwordUpdateRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String responseString = response.body().string();
                            JsonObject jsonObject = new Gson().fromJson(responseString, JsonObject.class);
                            String token = jsonObject.get("token").getAsString();
                            Log.d("fragmentProfile", "token in password : "+ token);
//                            decodeJWT(token);
                            if (Objects.equals(token, "INVALID_TOKEN")) {
                                Snackbar.make(view, "Old Password Incorrect", Snackbar.LENGTH_SHORT)
                                        .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                                        .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                                        .show();
                            } else {
                                Snackbar.make(view, "Password updated successfully", Snackbar.LENGTH_SHORT)
                                        .setBackgroundTint(getResources().getColor(R.color.green, null))
                                        .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                                        .show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("fragmentProfile", "password updating failed: " + e.getMessage());
                            Toast.makeText(mContext, "error in updating profile", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.d("fragmentProfile", "password update failed first: " + response);
                        Snackbar.make(view, "Error updating password", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                                .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                                .show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.d("fragmentProfile", "password update failed second: " + t.getMessage());
                    Snackbar.make(view, "Error updating password", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                            .show();
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    superadminEmailEditText.getText().clear();
                    superadminOldPasswordEditText.getText().clear();
                    superadminNewPasswordEditText.getText().clear();
                }
            }, 2000);
        }
    }

    private boolean validateEmail(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }
    private boolean validatePassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    private void decodeJWT(String token) {
        try {
            String secretKey = BuildConfig.API_KEY_NAME;
            byte[] decodedBytes = Base64.getDecoder().decode(secretKey);
            SecretKey secretKeySpec = Keys.hmacShaKeyFor(decodedBytes);
            // Parse the token
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKeySpec)
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Failed to decode JWT", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleRole(String token) {
        try {
            String secretKey = BuildConfig.API_KEY_NAME;
            byte[] decodedBytes = Base64.getDecoder().decode(secretKey);
            SecretKey secretKeySpec = Keys.hmacShaKeyFor(decodedBytes);
            // Parse the token
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKeySpec)
                    .build()
                    .parseSignedClaims(token);

            // Extract user role from claims
            String role = claims.getPayload().get("role", String.class);
            if ("SUPER_ADMIN".equals(role)) {
                saveToken(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Failed to decode JWT", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("superadmin", token);
        editor.apply();
        editor.commit();
    }
    private void removeToken() {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("superadmin");
            editor.apply();
        }
    }

    private void initializeViews() {
        sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        RetrofitService retrofitService = new RetrofitService();
        superadminService = retrofitService.getRetrofit().create(SuperAdminService.class);

        initialToken = sharedPreferences.getString("superadmin", "");
        String secretKey = BuildConfig.API_KEY_NAME;
        byte[] decodedBytes = Base64.getDecoder().decode(secretKey);
        SecretKey secretKeySpec = Keys.hmacShaKeyFor(decodedBytes);
        // Parse the token
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build()
                .parseSignedClaims(initialToken);
        superadminEmail = claims.getPayload().get("sub", String.class);
        superadminService.getAll("Bearer " + initialToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        String responseString = response.body().string();
                        JsonArray jsonArray = new Gson().fromJson(responseString, JsonArray.class);
                        Log.d("fragmentProfile", responseString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("fragmentProfile", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
