package com.biog.unihiveandroid.dashboard.superadmin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.DataListener;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.adapter.SuperadminRequestsFragmentAdapter;
import com.biog.unihiveandroid.adapter.SuperadminStudentsFragmentAdapter;
import com.biog.unihiveandroid.model.Request;
import com.biog.unihiveandroid.model.Student;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.SuperAdminService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.Instant;
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

public class FragmentRequests extends Fragment {
    private Context mContext;
    private GridView requestsGridView;
    private SharedPreferences sharedPreferences;
    private SuperAdminService superadminService;
    private String token;
    private JsonArray requestsData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_superadmin_requests, container, false);
        mContext = requireContext();
        TextView fragmentAdminsTitle = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_title);
        fragmentAdminsTitle.setText(R.string.requests_title);
        ImageButton searchIcon = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_search_icon);
        searchIcon.setVisibility(View.VISIBLE);

        initializeViews();
        requestsGridView = rootView.findViewById(R.id.superadmin_requests_grid_view);
        fetchRequests(new DataListener() {
            @Override
            public void onDataReceived(JsonArray requestsData) {
                List<Request> requestsList = new ArrayList<>();
                if (requestsData != null) {
                    for (JsonElement element : requestsData) {
                        JsonObject requestObject = element.getAsJsonObject();
                        Request request = new Request();
                        request.setFirstName(requestObject.get("firstName").getAsString());
                        request.setLastName(requestObject.get("lastName").getAsString());
                        request.setCreatedAt(Instant.parse(requestObject.get("createdAt").getAsString()));
                        requestsList.add(request);
                    }
                    SuperadminRequestsFragmentAdapter adapter = new SuperadminRequestsFragmentAdapter(mContext,requestsList);
                    requestsGridView.setAdapter(adapter);
                }
            }
            @Override
            public void onFetchFailure(Throwable t) {
                Log.d("fragmentrequests","fetch failure : "+ String.valueOf(t));
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private void fetchRequests(DataListener listener) {
        superadminService.listRequests("Bearer " + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    requestsData = new Gson().fromJson(responseBody, JsonArray.class);
                    Log.d("fragmentrequests","requests data : "+ requestsData);
                    listener.onDataReceived(requestsData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("framgentrequests","event failure : "+ String.valueOf(t));
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
