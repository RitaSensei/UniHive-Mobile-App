package com.biog.unihiveandroid.dashboard.superadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.adapter.SuperadminDashboardFragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FragmentDashboard extends Fragment {
    GridView superadminDashboardGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_superadmin_dashboard, container, false);

        superadminDashboardGridView = rootView.findViewById(R.id.dashboard_grid_view);

        ArrayList<Object> tableNames = new ArrayList<>(Arrays.asList("Admins", "Clubs","Events", "Schools", "Students", "Pending Sign up Requests"));

        SuperadminDashboardFragmentAdapter adapter = new SuperadminDashboardFragmentAdapter(requireContext(), tableNames);
        superadminDashboardGridView.setAdapter(adapter);

        superadminDashboardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment selectedFragment = null;
                switch (position) {
                    case 0:
                        selectedFragment = new FragmentAdmins();
                        break;
                    case 1:
                        selectedFragment = new FragmentClubs();
                        break;
                    case 2:
                        selectedFragment = new FragmentEvents();
                        break;
                    case 3:
                        selectedFragment = new FragmentSchools();
                        break;
                    case 4:
                        selectedFragment = new FragmentStudents();
                        break;
                    case 5:
                        selectedFragment = new FragmentRequests();
                        break;
                }
                if (selectedFragment != null) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return rootView;
    }
}
