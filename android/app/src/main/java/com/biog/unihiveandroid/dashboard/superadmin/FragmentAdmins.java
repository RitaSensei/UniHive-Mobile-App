package com.biog.unihiveandroid.dashboard.superadmin;

import static android.content.Context.MODE_PRIVATE;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.biog.unihiveandroid.DataListener;
import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.adapter.SuperadminAdminsFragmentAdapter;
import com.biog.unihiveandroid.model.Admin;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.SuperAdminService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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

public class FragmentAdmins extends Fragment {
    private Context mContext;
    private GridView adminsGridView;
    private SharedPreferences sharedPreferences;
    private SuperAdminService superadminService;
    private SuperadminAdminsFragmentAdapter adapter;
    private String token;
    private JsonArray adminsData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_superadmin_admins, container, false);
        mContext = requireContext();
        Toolbar toolbar = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar);
        TextView fragmentAdminsTitle = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_title);
        fragmentAdminsTitle.setText(R.string.admins_table_text);
        ImageButton searchButton = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_search_icon);
        searchButton.setVisibility(View.VISIBLE);
        initializeViews();
        adminsGridView = rootView.findViewById(R.id.superadmin_admins_grid_view);
        fetchAdmins(new DataListener() {
            @Override
            public void onDataReceived(JsonArray adminsData) {
                List<Admin> adminsList = new ArrayList<>();
                if (adminsData != null) {
                    for (JsonElement element : adminsData) {
                        JsonObject eventObject = element.getAsJsonObject();
                        Admin admin = new Admin();
                        admin.setFirstName(eventObject.get("firstName").getAsString());
                        admin.setLastName(eventObject.get("lastName").getAsString());
                        adminsList.add(admin);
                    }
                    adapter = new SuperadminAdminsFragmentAdapter(mContext,adminsList);
                    adminsGridView.setAdapter(adapter);
                }
            }
            @Override
            public void onFetchFailure(Throwable t) {
                Log.d("fragmentadmins","fetch failure : "+ String.valueOf(t));
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private void fetchAdmins(DataListener listener) {
        superadminService.listAdmins("Bearer " + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    adminsData = new Gson().fromJson(responseBody, JsonArray.class);
                    Log.d("fragmentadmins","admins data : "+ adminsData);
                    listener.onDataReceived(adminsData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("framgentadmins","event failure : "+ String.valueOf(t));
                listener.onFetchFailure(t);
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initializeViews() {
        sharedPreferences = mContext.getSharedPreferences("MyPrefs", MODE_PRIVATE);
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
    }
}
