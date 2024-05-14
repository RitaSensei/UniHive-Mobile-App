package com.biog.unihiveandroid.ui.clubs;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.ClubDataListener;
import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.adapter.ClubFragmentClubAdapter;
import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.model.Club;
import com.biog.unihiveandroid.service.ClubService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.StudentService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
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

public class FragmentClubs extends Fragment {
    private Context mContext;
    ListView clubsListView;
    private SharedPreferences sharedPreferences;
    private ClubService clubService;
    private StudentService studentService;
    private Date expirationTime;
    private String token,studentEmail;
    private boolean isLoggedIn=false, isLoading = true;
    private JsonObject studentData;
    private JsonArray clubsData;

    public FragmentClubs() {
        // Required empty public constructor
    }

    public static FragmentClubs newInstance(String param1, String param2) {
        FragmentClubs fragment = new FragmentClubs();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clubs, container, false);
        mContext = requireContext();
        // Access the textview in Toolbar and change its text
        TextView toolbarTitle =((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_title);
        toolbarTitle.setText(R.string.ic_clubs);
        // Access the search icon in Toolbar and change its visibility
        ImageButton searchIcon = ((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_search_icon);
        searchIcon.setVisibility(View.VISIBLE);

        initializeViews();
        clubsListView = rootView.findViewById(R.id.clubs_list_view);

        fetchClubs(new ClubDataListener() {
            @Override
            public void onClubDataReceived(JsonArray clubData) {
                Log.d("FragmentClub", "onCreateView club data : "+ clubsData);
                // Create a list to hold club objects
                List<Club> clubList = new ArrayList<>();
                if (clubsData != null) {
                    for (JsonElement element : clubData) {
                        JsonObject clubObject = element.getAsJsonObject();
                        Club club = new Club();
                        club.setId(clubObject.get("id").getAsString());
                        club.setClubName(clubObject.get("clubName").getAsString());
                        club.setClubDescription(clubObject.get("clubDescription").getAsString());
                        club.setClubLogo(clubObject.get("clubLogo").getAsString());
                        club.setClubRating(clubObject.get("clubRating").getAsFloat());
                        clubList.add(club);
                    }
                    // Sort the club list based on ratings in descending order
                    clubList.sort(new Comparator<Club>() {
                        @Override
                        public int compare(Club c1, Club c2) {
                            return Float.compare(c2.getClubRating(), c1.getClubRating());
                        }
                    });
                    ClubFragmentClubAdapter adapter = new ClubFragmentClubAdapter(mContext, clubList);
                    clubsListView.setAdapter(adapter);
                }
            }
            @Override
            public void onFetchFailure(Throwable t) {
                Log.e("FragmentHome", "Fetch club failed: " + t.getMessage());
                Toast.makeText(requireContext(), "Failed to fetch club data", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void fetchClubs(ClubDataListener listener) {
        if (!expirationTime.before(new Date())) {
            studentService.getStudent("Bearer "+token,studentEmail).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    try {
                        String responseBody = response.body().string();
                        studentData = new Gson().fromJson(responseBody, JsonObject.class);
                        Log.d("unihivehome"," response student " + studentData);
                    } catch (IOException e) {
                        Log.e("unihivehome", "Error reading response body: " + e.getMessage());
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        clubService.getClubs().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    clubsData = new Gson().fromJson(responseBody, JsonArray.class);
                    Log.d("unihivehome","club response : "+ clubsData);
                    listener.onClubDataReceived(clubsData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("unihivehome","club failure : "+ String.valueOf(t));
                listener.onFetchFailure(t);
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        RetrofitService retrofitService = new RetrofitService();
        studentService = retrofitService.getRetrofit().create(StudentService.class);
        clubService = retrofitService.getRetrofit().create(ClubService.class);

        token = sharedPreferences.getString("student", "");
        String secretKey = BuildConfig.API_KEY_NAME;
        byte[] decodedBytes = Base64.getDecoder().decode(secretKey);
        SecretKey secretKeySpec = Keys.hmacShaKeyFor(decodedBytes);
        // Parse the token
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build()
                .parseSignedClaims(token);
        studentEmail = claims.getPayload().get("sub", String.class);
        expirationTime = claims.getPayload().getExpiration();
        if (!token.equals("")) {
            isLoggedIn = true;
        }
        if (expirationTime.before(new Date())) {
            isLoggedIn = false;
            if (!token.equals("")) {
                sharedPreferences.edit().remove("student").apply();
                startActivity(new Intent(requireActivity(), LoginActivity.class));
            }
        }
    }
}