package com.biog.unihiveandroid.ui.events;

import static android.content.Context.MODE_PRIVATE;
import static java.time.Instant.now;

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
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.EventDataListener;
import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.adapter.UpcomingEventsFragmentEventsAdapter;
import com.biog.unihiveandroid.adapter.UpcomingEventsFragmentHomeAdapter;
import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.model.Club;
import com.biog.unihiveandroid.model.Event;
import com.biog.unihiveandroid.service.EventService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.StudentService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.Instant;
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

public class FragmentEvents extends Fragment {
    private Context mContext;
    ListView upcomingEventsListView;
    private SharedPreferences sharedPreferences;
    private StudentService studentService;
    private EventService eventService;
    private Date expirationTime;
    private String token,studentEmail;
    private boolean isLoggedIn=false, isLoading = true;
    private JsonObject studentData;
    private JsonArray eventsData;

    public FragmentEvents() {}

    public static FragmentEvents newInstance(String param1, String param2) {
        FragmentEvents fragment = new FragmentEvents();
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
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        mContext = requireContext();
        // Access the textview in Toolbar and change its text
        TextView toolbarTitle =((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_title);
        toolbarTitle.setText(R.string.ic_events);
        // Access the search icon in Toolbar and change its visibility
        ImageButton searchIcon = ((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_search_icon);
        searchIcon.setVisibility(View.VISIBLE);

        initializeViews();
        upcomingEventsListView = rootView.findViewById(R.id.upcoming_events_list_view);

        fetchEvents(new EventDataListener() {
            @Override
            public void onEventDataReceived(JsonArray eventData) {
                Log.d("FragmentEvents", "onCreateView: "+ eventsData);
                List<Event> upcomingEventList = new ArrayList<>();
                if (eventsData != null) {
                    // Extract banner URLs from event objects
                    for (JsonElement element : eventsData) {
                        JsonObject eventObject = element.getAsJsonObject();
                        Event event = new Event();
                        event.setEventName(eventObject.get("eventName").getAsString());
                        event.setEventBanner(eventObject.get("eventBanner").getAsString());
                        event.setEventRating(eventObject.get("eventRating").getAsInt());
                        event.setStartTime(Instant.parse(eventObject.get("startTime").getAsString()));
                        event.setEndTime(Instant.parse(eventObject.get("endTime").getAsString()));
                        JsonObject clubObject = eventObject.get("club").getAsJsonObject();
                        Club club = new Club();
                        club.setClubName(clubObject.get("clubName").getAsString());
                        event.setClub(club);
                        upcomingEventList.add(event);
                    }
                    upcomingEventList.sort(new Comparator<Event>() {
                        @Override
                        public int compare(Event e1, Event e2) {
                            Instant startTime1 = e1.getStartTime();
                            Instant startTime2 = e2.getStartTime();
                            // Handle null cases
                            if (startTime1 == null && startTime2 == null) {
                                return 0; // Both dates are null, consider them equal
                            } else if (startTime1 == null) {
                                return 1; // Date1 is null, consider it later than date2
                            } else if (startTime2 == null) {
                                return -1; // Date2 is null, consider it later than date1
                            }

                            // Compare the Instant objects
                            return startTime1.compareTo(startTime2);
                        }
                    });
                    UpcomingEventsFragmentEventsAdapter adapter = new UpcomingEventsFragmentEventsAdapter(mContext, upcomingEventList);
                    upcomingEventsListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFetchFailure(Throwable t) {
                Log.e("FragmentEvents", "Fetch event failed: " + t.getMessage());
                Toast.makeText(requireContext(), "Failed to fetch event data", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private void fetchEvents(EventDataListener listener) {
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
        eventService.getEvents().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    eventsData = new Gson().fromJson(responseBody, JsonArray.class);
                    Log.d("unihivehome","event data : "+ eventsData);
                    listener.onEventDataReceived(eventsData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("unihivehome","event failure : "+ String.valueOf(t));
                listener.onFetchFailure(t);
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        RetrofitService retrofitService = new RetrofitService();
        studentService = retrofitService.getRetrofit().create(StudentService.class);
        eventService = retrofitService.getRetrofit().create(EventService.class);

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