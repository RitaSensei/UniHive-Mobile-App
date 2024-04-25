package com.biog.unihiveandroid;

import static androidx.navigation.Navigation.findNavController;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = findNavController(R.id.nav_host_fragment);
//        return navController.navigateUp() || super.onSupportNavigateUp();
//    }
}
