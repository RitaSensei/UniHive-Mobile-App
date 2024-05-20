package com.biog.unihiveandroid.dashboard.superadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.dashboard.superadmin.FragmentProfile;
import com.biog.unihiveandroid.service.ClubService;
import com.biog.unihiveandroid.service.EventService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.SuperAdminService;
import com.google.android.material.navigation.NavigationView;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class SuperAdminDashboardActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SuperAdminService superadminService;
    private String superadminEmail, token;
    private Date expirationTime;
    private boolean isLoggedIn = false;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_dashboard_page);
        toolbar = findViewById(R.id.superadmin_dashboard_toolbar);
        initializeViews();
        ImageButton logoutButton = toolbar.findViewById(R.id.superadmin_dashboard_toolbar_logout_icon);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        drawerLayout = findViewById(R.id.superadmin_drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.superadmin_navigation_view);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentDashboard())
                    .commit();
            MenuItem dashboardItem = navigationView.getMenu().findItem(R.id.dashboard_item);
            if (dashboardItem != null) {
                dashboardItem.setChecked(true);
            }
        }
        // Set an OnClickListener for the profile item
        TextView profileItem = findViewById(R.id.superadmin_profile_item);
        profileItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < navigationView.getMenu().size(); i++) {
                    MenuItem menuItem = navigationView.getMenu().getItem(i);
                    menuItem.setChecked(false);
                }
                profileItem.setBackgroundResource(R.drawable.drawer_navigation_item_shape);
                profileItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_superadmin_profile_white, 0, 0, 0);
                profileItem.setTextColor(getColor(R.color.white));
                drawerLayout.closeDrawer(GravityCompat.START);
                Fragment selectedFragment = new FragmentProfile();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                profileItem.setBackgroundColor(Color.TRANSPARENT);
                profileItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_superadmin_profile_black, 0, 0, 0);
                profileItem.setTextColor(getColor(R.color.black));
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

    // Add a method to update the selected item in the drawer navigation
    public void updateSelectedItem(int position) {
        NavigationView navigationView = findViewById(R.id.superadmin_navigation_view);
        MenuItem selectedItem = navigationView.getMenu().getItem(position+1);
        if (selectedItem != null) {
            for (int i = 0; i < navigationView.getMenu().size(); i++) {
                MenuItem menuItem = navigationView.getMenu().getItem(i);
                menuItem.setChecked(false);
            }
            selectedItem.setChecked(true);
        }
    }

    private void logout() {
        removeToken();
        Intent intent = new Intent(SuperAdminDashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void removeToken() {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("superadmin");
            editor.apply();
        }
    }

    private void initializeViews() {
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        RetrofitService retrofitService = new RetrofitService();
        superadminService = retrofitService.getRetrofit().create(SuperAdminService.class);

        token = sharedPreferences.getString("superadmin", "");
        String secretKey = BuildConfig.API_KEY_NAME;
        byte[] decodedBytes = Base64.getDecoder().decode(secretKey);
        SecretKey secretKeySpec = Keys.hmacShaKeyFor(decodedBytes);
        // Parse the token
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build()
                .parseSignedClaims(token);
//        superadminEmail = claims.getPayload().get("sub", String.class);
        expirationTime = claims.getPayload().getExpiration();
        if (!token.equals("")) {
            isLoggedIn = true;
        }
        if (expirationTime.before(new Date())) {
            isLoggedIn = false;
            if (!token.equals("")) {
                sharedPreferences.edit().remove("superadmin").apply();
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
    }
}
