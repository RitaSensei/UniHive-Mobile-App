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
import com.biog.unihiveandroid.SchoolDataListener;
import com.biog.unihiveandroid.adapter.SuperadminSchoolsFragmentAdapter;
import com.biog.unihiveandroid.adapter.SuperadminStudentsFragmentAdapter;
import com.biog.unihiveandroid.model.School;
import com.biog.unihiveandroid.model.Student;
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

public class FragmentStudents extends Fragment {
    private Context mContext;
    private GridView studentsGridView;
    private SharedPreferences sharedPreferences;
    private SuperAdminService superadminService;
    private String token;
    private JsonArray studentsData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_superadmin_students, container, false);
        mContext = requireContext();
        TextView fragmentAdminsTitle = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_title);
        fragmentAdminsTitle.setText(R.string.students_table_text);
        ImageButton searchIcon = ((SuperAdminDashboardActivity) requireActivity()).findViewById(R.id.superadmin_dashboard_toolbar_search_icon);
        searchIcon.setVisibility(View.VISIBLE);

        initializeViews();
        studentsGridView = rootView.findViewById(R.id.superadmin_students_grid_view);
        fetchStudents(new DataListener() {
            @Override
            public void onDataReceived(JsonArray studentsData) {
                List<Student> studentsList = new ArrayList<>();
                if (studentsData != null) {
                    for (JsonElement element : studentsData) {
                        JsonObject studentObject = element.getAsJsonObject();
                        Student student = new Student();
                        student.setFirstName(studentObject.get("firstName").getAsString());
                        student.setLastName(studentObject.get("lastName").getAsString());
                        studentsList.add(student);
                    }
                    SuperadminStudentsFragmentAdapter adapter = new SuperadminStudentsFragmentAdapter(mContext,studentsList);
                    studentsGridView.setAdapter(adapter);
                }
            }
            @Override
            public void onFetchFailure(Throwable t) {
                Log.d("fragmentstudents","fetch failure : "+ String.valueOf(t));
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private void fetchStudents(DataListener listener) {
        superadminService.listStudents("Bearer " + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    studentsData = new Gson().fromJson(responseBody, JsonArray.class);
                    Log.d("framgentstudents","events data : "+ studentsData);
                    listener.onDataReceived(studentsData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("framgentstudents","event failure : "+ String.valueOf(t));
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
