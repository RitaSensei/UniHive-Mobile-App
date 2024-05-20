package com.biog.unihiveandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.authentication.WelcomePage;
import com.biog.unihiveandroid.ui.calendar.FragmentCalendar;
import com.biog.unihiveandroid.ui.clubs.FragmentClubs;
import com.biog.unihiveandroid.ui.events.FragmentEvents;
import com.biog.unihiveandroid.ui.home.FragmentHome;
import com.biog.unihiveandroid.ui.profile.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressBar progressBar;
    View fragmentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        progressBar = findViewById(R.id.progress_bar_circular_main_activity);
        fragmentContainer = findViewById(R.id.nav_host_fragment);
        ImageButton settingsIcon = toolbar.findViewById(R.id.main_toolbar_settings_icon);
        ImageButton searchIcon = toolbar.findViewById(R.id.main_toolbar_search_icon);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

//        searchIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, SearchActivity.class));
//            }
//        });

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    replaceFragment(new FragmentHome(), R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
                    return true;
                } else if (itemId == R.id.action_events) {
                    replaceFragment(new FragmentEvents(), R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
                    return true;
                } else if (itemId == R.id.action_clubs) {
                    replaceFragment(new FragmentClubs(), R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
                    return true;
                } else if (itemId == R.id.action_calendar) {
                    replaceFragment(new FragmentCalendar(), R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
                    return true;
                } else if (itemId == R.id.action_profile) {
                    replaceFragment(new FragmentProfile(), R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
                    return true;
                }
                return false;
            }
        });

    }

    public void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.GONE);
    }

    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);
    }

    public void replaceFragment(Fragment fragment, @AnimRes int enterAnim, @AnimRes int exitAnim, @AnimRes int popEnterAnim, @AnimRes int popExitAnim) {
        showLoadingIndicator();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = findNavController(R.id.nav_host_fragment);
//        return navController.navigateUp() || super.onSupportNavigateUp();
//    }
}
