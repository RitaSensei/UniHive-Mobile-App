package com.biog.unihiveandroid.authentication;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.RegisterRequest;
import com.biog.unihiveandroid.model.SchoolsNames;
import com.biog.unihiveandroid.service.AuthenticationService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.SuperAdminService;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private String selectedFileName;
    private EditText firstName;
    private EditText lastName;
    private EditText cneOrMassar;
    private EditText numApogee;
    private Button importSchoolCardBtn;
    private Spinner schoolsList;
    private EditText emailAddressSignupText;
    private EditText passwordSignupText;
    private EditText confirmPasswordSignupText;
    private AuthenticationService authenticationService;
    private SuperAdminService superAdminService;
    String selectedSchoolName;
    private long selectedFileSize;
    private Uri selectedFileUri;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        initializeViews();
        pickMedia = registerForActivityResult(new PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);
                // Extract the file name from the URI and set it to the selectedFileName variable
                selectedFileName = extractFileNameFromUri(uri);
                selectedFileSize = getFileSize(uri);
                selectedFileUri = uri;
                // Update the text of the importSchoolCardBtn with the selected file name
                importSchoolCardBtn.setText(selectedFileName);
                importSchoolCardBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.round_check_circle, 0);
            } else {
                Toast.makeText(this, "No media selected", Toast.LENGTH_SHORT).show();
            }
        });
        TextView loginText = findViewById(R.id.login_existing_account_button);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    public void importSchoolCardOnClick(View view) {
        // Launch the photo picker and let the user choose images and videos.
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(PickVisualMedia.ImageAndVideo.INSTANCE)
                .build());
    }

    public void onSignupBtnClick(View view) {
        String firstName = this.firstName.getText().toString().trim();
        String lastName = this.lastName.getText().toString().trim();
        String cneOrMassar = this.cneOrMassar.getText().toString().trim();
        String numApogee = this.numApogee.getText().toString().trim();
        String email = this.emailAddressSignupText.getText().toString().trim();
        String password = this.passwordSignupText.getText().toString().trim();
        String confirmPassword = this.confirmPasswordSignupText.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || cneOrMassar.isEmpty() || numApogee.isEmpty() || selectedSchoolName == null || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedFileSize > 10 * 1024 * 1024) {
            Snackbar.make(view, "File size too large! It should be less than 10MB", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .show();
            return;
        }
        if (!validateEmail(email)) {
            Snackbar.make(view, "Invalid email address!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .show();
            return;
        }
        if (!validatePassword(password)) {
            passwordSignupText.setError("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character!");
            return;
        }
        if (!validatePassword(confirmPassword)) {
            confirmPasswordSignupText.setError("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            Snackbar.make(view, "Passwords do not match!", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getResources().getColor(R.color.red_light, null))
                    .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                    .show();
            return;
        }
        // Upload the selected file to GCP Storage
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedFileUri);
            assert inputStream != null;
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            String selectedFileType = selectedFileName.substring(selectedFileName.lastIndexOf(".") + 1);
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/" + selectedFileType), fileBytes);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", selectedFileName, fileRequestBody);
            List<MultipartBody.Part> filesParts = new ArrayList<>();
            filesParts.add(filePart);
            superAdminService.uploadFiles(filesParts).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("PhotoPicker", "Upload failed in the else : " + response +" and  size is " + selectedFileSize);
                        Toast.makeText(SignupActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Log.d("PhotoPicker", "Upload failed in the catch : " + t);
                    Toast.makeText(SignupActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch(IOException exception) {
            Toast.makeText(SignupActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
        }

        RegisterRequest registerRequest = new RegisterRequest(
                firstName, lastName,cneOrMassar,Integer.parseInt(numApogee),"https://storage.googleapis.com/unihive-files/" +selectedFileName,selectedSchoolName,email,password);
        authenticationService.register(registerRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                } else {
                    Log.d("Signup", "Signup failed in the else : " + response.toString());
                    Toast.makeText(SignupActivity.this, "Signup failed!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("Signup", "Signup failed in the catch : " + t);
                Toast.makeText(SignupActivity.this, "Signup failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateEmail(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }
    private boolean validatePassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    };

    private String extractFileNameFromUri(Uri uri) {
        String scheme = uri.getScheme();
        String fileName = null;
        if (scheme != null && scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        fileName = cursor.getString(displayNameIndex);
                    }
                }
            }
        }
        return fileName;
    }

    private long getFileSize(Uri uri) {
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (sizeIndex != -1) {
                    return cursor.getLong(sizeIndex);
                }
            }
        }
        return 0;
    }

    private void initializeViews() {
        firstName = findViewById(R.id.editFirstnameText);
        lastName = findViewById(R.id.editLastnameText);
        cneOrMassar = findViewById(R.id.editCneMassarText);
        numApogee = findViewById(R.id.editNumApogeeText);
        importSchoolCardBtn= findViewById(R.id.schoolCardUpload);
        schoolsList = findViewById(R.id.selectSchool);
        emailAddressSignupText = findViewById(R.id.editEmailAddressSignupText);
        passwordSignupText = findViewById(R.id.editPasswordSignupText);
        confirmPasswordSignupText= findViewById(R.id.editConfirmPasswordSignupText);
        RetrofitService retrofitService = new RetrofitService();
        authenticationService = retrofitService.getRetrofit().create(AuthenticationService.class);
        superAdminService = retrofitService.getRetrofit().create(SuperAdminService.class);
        // Extract school names from enum values
        List<String> schoolNames = new ArrayList<>();
        for (SchoolsNames school : SchoolsNames.values()) {
            schoolNames.add(school.getSchoolName());
        }
        schoolNames.sort(String.CASE_INSENSITIVE_ORDER);
        // Create an ArrayAdapter using the enum values
        ArrayAdapter<String> adpater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, schoolNames);
        adpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the Spinner
        schoolsList.setAdapter(adpater);
        // Set a listener to handle item selection
        schoolsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSchoolName = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
