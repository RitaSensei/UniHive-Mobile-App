package com.biog.unihiveandroid.dashboard.superadmin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.adapter.SuperadminDashboardFragmentAdapter;
import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.service.ClubService;
import com.biog.unihiveandroid.service.EventService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.StudentService;
import com.biog.unihiveandroid.service.SuperAdminService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDashboard extends Fragment {
    private Context mContext;
    private GridView superadminDashboardGridView;
    private SharedPreferences sharedPreferences;
    private SuperAdminService superadminService;
    private ClubService clubService;
    private EventService eventService;
    private String superadminEmail, token;
    private Date expirationTime;
    private boolean isLoggedIn = false;
    private JsonArray superadminData;
    SuperadminDashboardFragmentAdapter adapter;
    private ArrayList<Object> tableNames;
    private ArrayList<Integer> tableCounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_superadmin_dashboard, container, false);
        mContext = requireContext();
        TextView fragmentDashboardTitle = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_title);
        fragmentDashboardTitle.setText(R.string.superadmin_dashboard_title);
        ImageButton searchIcon = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_search_icon);
        searchIcon.setVisibility(View.GONE);
        superadminDashboardGridView = rootView.findViewById(R.id.dashboard_grid_view);
        initializeViews();
        ((SuperAdminDashboardActivity) requireActivity()).showLoadingIndicator();
        fetchData();

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
                    // Call the activity's method to update the selected item in the drawer navigation
                    ((SuperAdminDashboardActivity) requireActivity()).updateSelectedItem(position);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return rootView;
    }

    private void fetchData() {
        if (!expirationTime.before(new Date())) {
            superadminService.getAllCounts("Bearer "+token).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.body() != null) {
                        try {
                            String responseBody = response.body().string();
                            superadminData = new Gson().fromJson(responseBody, JsonArray.class);
                            Log.d("fragmentsuperadmindashboard"," response superadmin " + superadminData);
                            tableNames = new ArrayList<>(Arrays.asList("Admins", "Clubs","Events", "Schools", "Students", "Requests"));
                            tableCounts = new ArrayList<>(Collections.nCopies(tableNames.size(), 0));
                            for (int i = 0; i < superadminData.size(); i++) {
                                int numberOfRowsData = superadminData.get(i).getAsInt();
                                tableCounts.set(i, numberOfRowsData);
                            }
                            adapter = new SuperadminDashboardFragmentAdapter(mContext, tableNames, tableCounts);
                            superadminDashboardGridView.setAdapter(adapter);
                        } catch (IOException e) {
                            Log.e("fragmentsuperadmindashboard", "Error reading response body: " + e.getMessage());
                        }
                    }
                    ((SuperAdminDashboardActivity) requireActivity()).hideLoadingIndicator();
                }
                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    ((SuperAdminDashboardActivity) requireActivity()).hideLoadingIndicator();
                }
            });
        }
    }

    private void initializeViews() {
        sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        RetrofitService retrofitService = new RetrofitService();
        superadminService = retrofitService.getRetrofit().create(SuperAdminService.class);
        clubService = retrofitService.getRetrofit().create(ClubService.class);
        eventService = retrofitService.getRetrofit().create(EventService.class);

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
                startActivity(new Intent(requireActivity(), LoginActivity.class));
            }
        }
    }
}
