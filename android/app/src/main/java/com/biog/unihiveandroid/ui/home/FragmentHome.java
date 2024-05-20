package com.biog.unihiveandroid.ui.home;

import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.ClubDataListener;
import com.biog.unihiveandroid.EventDataListener;
import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.adapter.ClubFragmentHomeAdapter;
import com.biog.unihiveandroid.adapter.UpcomingEventsFragmentHomeAdapter;
import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.model.Club;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Event;
import com.biog.unihiveandroid.service.ClubService;
import com.biog.unihiveandroid.service.EventService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.StudentService;
import com.biog.unihiveandroid.ui.clubs.FragmentClubs;
import com.biog.unihiveandroid.ui.events.FragmentEvents;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class FragmentHome extends Fragment {
    private int currentPosition = 0;
    private GridView clubsGridView, upcomingEventsGridView;
    private ImageButton seeAllUpcomingEventsButton, seeAllClubsButton, previousButton, nextButton;
    private ImageSwitcher trendingEventsSwitcher;
    private StudentService studentService;
    private EventService eventService;
    private ClubService clubService;
    private boolean isLoggedIn=false, isLoading = true;
    private SharedPreferences sharedPreferences;
    private Date expirationTime;
    private String token,studentEmail;
    private JsonObject studentData;
    private JsonArray clubsData,eventsData;

    public FragmentHome() {}

    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Access the textview in Toolbar and change its text
        TextView toolbarTitle =((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_title);
        toolbarTitle.setText(R.string.ic_home);
        // Access the search icon in Toolbar and change its visibility
        ImageButton searchIcon = ((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_search_icon);
        searchIcon.setVisibility(View.GONE);

        initializeViews();
        trendingEventsSwitcher = rootView.findViewById(R.id.trending_events_switcher_home);
        previousButton = rootView.findViewById(R.id.previous_button_switcher);
        nextButton = rootView.findViewById(R.id.next_button_switcher);
        clubsGridView = rootView.findViewById(R.id.clubs_grid_view);
        seeAllClubsButton = rootView.findViewById(R.id.see_all_button_clubs_switcher);
        upcomingEventsGridView = rootView.findViewById(R.id.upcoming_events_grid_view);
        seeAllUpcomingEventsButton = rootView.findViewById(R.id.see_all_button_upcoming_events_switcher);
        ((MainActivity) requireActivity()).showLoadingIndicator();
        fetchData();
        fetchEvents(new EventDataListener() {
            @Override
            public void onEventDataReceived(JsonArray eventData) {
                Log.d("FragmentHome", "onCreateView: "+ eventsData);
                // Create a list to hold banner URLs
                List<String> bannerUrls = new ArrayList<>();
                List<Event> upcomingEventList = new ArrayList<>();
                if (eventsData != null) {
                    // Extract banner URLs from event objects
                    for (JsonElement element : eventsData) {
                        JsonObject eventObject = element.getAsJsonObject();
                        Event event = new Event();
                        event.setEventName(eventObject.get("eventName").getAsString());
                        event.setEventBanner(eventObject.get("eventBanner").getAsString());
                        event.setEventRating(eventObject.get("eventRating").getAsInt());
                        upcomingEventList.add(event);
//                        String bannerUrl = eventObject.get("eventBanner").getAsString();
                        bannerUrls.add(event.getEventBanner());
                    }
                    trendingEventsSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                        @Override
                        public View makeView() {
                            ImageView imageView = new ImageView(getContext());
                            imageView.setAdjustViewBounds(true);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            return imageView;
                        }
                    });
                    // Load the first image into the ImageSwitcher
                    Glide.with(requireContext())
                            .load(bannerUrls.get(0))
                            .into((ImageView) trendingEventsSwitcher.getCurrentView());

                    // Update the ImageSwitcher with banner images
                    trendingEventsSwitcher.setInAnimation(requireContext(), R.anim.slide_in_animation);
                    trendingEventsSwitcher.setOutAnimation(requireContext(), R.anim.slide_out_animation);

                    previousButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentPosition--;
                            if (currentPosition < 0) {
                                currentPosition = bannerUrls.size() - 1;
                            }
                            Glide.with(requireContext())
                                    .load(bannerUrls.get(currentPosition))
                                    .into((ImageView) trendingEventsSwitcher.getNextView());
                            trendingEventsSwitcher.showPrevious();
                        }
                    });

                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentPosition++;
                            if (currentPosition == bannerUrls.size()) {
                                currentPosition = 0;
                            }
                            Glide.with(requireContext())
                                    .load(bannerUrls.get(currentPosition))
                                    .into((ImageView) trendingEventsSwitcher.getNextView());
                            trendingEventsSwitcher.showNext();
                        }
                    });

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
                    List<Event> threeNextEvents = upcomingEventList.subList(0, Math.min(3, upcomingEventList.size()));
                    UpcomingEventsFragmentHomeAdapter upcomingEventsAdapter = new UpcomingEventsFragmentHomeAdapter(requireContext(), threeNextEvents);
                    upcomingEventsGridView.setAdapter(upcomingEventsAdapter);
                }
                ((MainActivity) requireActivity()).hideLoadingIndicator();
            }

            @Override
            public void onFetchFailure(Throwable t) {
                Log.e("FragmentHome", "Fetch event failed: " + t.getMessage());
                Toast.makeText(requireContext(), "Failed to fetch event data", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).hideLoadingIndicator();
            }
        });
        seeAllUpcomingEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentEvents();
                ((MainActivity) requireActivity()).replaceFragment(fragment, R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
                BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).findViewById(R.id.bottom_navigation_bar);
                bottomNavigationView.setSelectedItemId(R.id.action_events);
            }
        });

        fetchClubs(new ClubDataListener() {
            @Override
            public void onClubDataReceived(JsonArray clubData) {
                Log.d("FragmentHome", "onCreateView club data : "+ clubsData);
                // Create a list to hold club objects
                List<Club> clubList = new ArrayList<>();
                if (clubsData != null) {
                    for (JsonElement element : clubData) {
                        JsonObject clubObject = element.getAsJsonObject();
                        Club club = new Club();
                        club.setId(clubObject.get("id").getAsString());
                        club.setClubName(clubObject.get("clubName").getAsString());
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
                    // Display the top three clubs with highest ratings
                    List<Club> topThreeClubs = clubList.subList(0, Math.min(3, clubList.size()));
                    ClubFragmentHomeAdapter adapter = new ClubFragmentHomeAdapter(requireContext(), topThreeClubs);
                    clubsGridView.setAdapter(adapter);
                }
                ((MainActivity) requireActivity()).hideLoadingIndicator();
            }
            @Override
            public void onFetchFailure(Throwable t) {
                Log.e("FragmentHome", "Fetch club failed: " + t.getMessage());
                Toast.makeText(requireContext(), "Failed to fetch club data", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).hideLoadingIndicator();
            }
        });
        seeAllClubsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentClubs();
                ((MainActivity) requireActivity()).replaceFragment(fragment, R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
                // Update bottom navigation item
                BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).findViewById(R.id.bottom_navigation_bar);
                bottomNavigationView.setSelectedItemId(R.id.action_clubs);
            }
        });
        return rootView;
    }
    private void fetchData() {
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
    }

    private void fetchClubs(ClubDataListener listener) {
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

    private void fetchEvents(EventDataListener listener) {
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
        clubService = retrofitService.getRetrofit().create(ClubService.class);
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