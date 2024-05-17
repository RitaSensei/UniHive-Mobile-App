package com.biog.unihiveandroid.dashboard.superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.ui.profile.FragmentProfile;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class SuperAdminDashboardActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_dashboard_page);
        Toolbar toolbar = findViewById(R.id.superadmin_dashboard_toolbar);
        setSupportActionBar(toolbar);

//        ImageButton profileIcon = toolbar.findViewById(R.id.superadmin_dashboard_toolbar_profile_icon);
//        profileIcon.setOnClickListener(v -> {
//            startActivity(new Intent(SuperAdminDashboardActivity.this, FragmentProfile.class));
//        });

        drawerLayout = findViewById(R.id.superadmin_drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.superadmin_navigation_view);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentDashboard())
                    .commit();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.dashboard_item) {
                    selectedFragment = new FragmentDashboard();
                } else if (itemId == R.id.admins_item) {
                    selectedFragment = new FragmentAdmins();
                } else if (itemId == R.id.clubs_item) {
                    selectedFragment = new FragmentClubs();
                } else if (itemId == R.id.events_item) {
                    selectedFragment = new FragmentEvents();
                } else if (itemId == R.id.schools_item) {
                    selectedFragment = new FragmentSchools();
                } else if (itemId == R.id.students_item) {
                    selectedFragment = new FragmentStudents();
                } else if (itemId == R.id.signup_requests_item) {
                    selectedFragment = new FragmentRequests();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    item.setChecked(true);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
