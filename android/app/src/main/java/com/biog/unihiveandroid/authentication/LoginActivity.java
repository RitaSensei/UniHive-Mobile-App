package com.biog.unihiveandroid.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.SettingsActivity;
import com.biog.unihiveandroid.ui.home.FragmentHome;

public class LoginActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    public void onCreateAccountBtnClick(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    public void onLoginBtnPageLoginClick(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish(); // Finish LoginActivity to prevent navigating back to it using the back button
    }

}
