package com.biog.unihiveandroid.ui.home;

import static android.content.Context.MODE_PRIVATE;
import static com.biog.unihiveandroid.ImageData.getClubsGridItems;
import static com.biog.unihiveandroid.ImageData.getUpcomingEventsGridItems;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import com.biog.unihiveandroid.BuildConfig;
import com.biog.unihiveandroid.EventDataListener;
import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.model.Club;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Event;
import com.biog.unihiveandroid.service.ClubService;
import com.biog.unihiveandroid.service.EventService;
import com.biog.unihiveandroid.service.RetrofitService;
import com.biog.unihiveandroid.service.StudentService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
//    private List<Integer> trendingEventsItems = getTrendingEventsSwitcherItems();
//    private List<Integer> clubsItems = getClubsGridItems();
//    private List<Integer> upcomingEventsItems = getUpcomingEventsGridItems();
    private GridView clubsGridView, upcomingEventsGridView;
    private ImageButton seeAllUpcomingEventsButton, seeAllClubsButton, previousButton, nextButton;
    private ImageSwitcher trendingEventsSwitcher;
    private StudentService studentService;
    private EventService eventService;
    private ClubService clubService;
    private boolean isLoggedIn=false, isLoading = true;
    private List<Club> clubs;
    private List<Event> events;
    private SharedPreferences sharedPreferences;
    private Date expirationTime;
    private String token,studentEmail;
    private JsonObject studentData;
    private JsonArray clubData,eventsData;

    public FragmentHome() {
        // Required empty public constructor
    }

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
//        clubsGridView = rootView.findViewById(R.id.clubs_grid_view);
//        seeAllClubsButton = rootView.findViewById(R.id.see_all_button_clubs_switcher);
//        seeAllUpcomingEventsButton = rootView.findViewById(R.id.see_all_button_upcoming_events_switcher);
//        upcomingEventsGridView = rootView.findViewById(R.id.upcoming_events_grid_view);

        fetchData(new EventDataListener() {
            @Override
            public void onEventDataReceived(JsonArray eventData) {
                Log.d("FragmentHome", "onCreateView: "+ eventsData);
                // Create a list to hold banner URLs
                List<String> bannerUrls = new ArrayList<>();
                if (eventsData != null) {
                    // Extract banner URLs from event objects
                    for (JsonElement element : eventsData) {
                        JsonObject eventObject = element.getAsJsonObject();
                        String bannerUrl = eventObject.get("eventBanner").getAsString();
                        bannerUrls.add(bannerUrl);
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

//                    Handler handler = new Handler();
//                    Runnable runnable = new Runnable() {
//                        int currentPosition = 0;
//                        @Override
//                        public void run() {
//                            currentPosition++;
//                            if (currentPosition == bannerUrls.size()) {
//                                currentPosition = 0;
//                            }
//                            Glide.with(requireContext())
//                                    .load(bannerUrls.get(currentPosition))
//                                    .into((ImageView) trendingEventsSwitcher.getNextView());
//                            trendingEventsSwitcher.showNext();
//                            handler.postDelayed(this, 5000); // Change image every 5 seconds
//                        }
//                    };
//                    handler.postDelayed(runnable, 5000); // Start the runnable after 5 seconds
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
                }
            }

            @Override
            public void onFetchFailure(Throwable t) {
                Log.e("FragmentHome", "Fetch failed: " + t.getMessage());
                Toast.makeText(requireContext(), "Failed to fetch event data", Toast.LENGTH_SHORT).show();
            }
        });


//        ArrayList<ClubModel> clubModelArrayList = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            clubModelArrayList.add(new ClubModel(5.0F, clubsItems.get(i)));
//        }
//        ClubFragmentHomeAdapter clubFragmentHomeAdapter = new ClubFragmentHomeAdapter(requireContext(), clubModelArrayList);
//        clubsGridView.setAdapter(clubFragmentHomeAdapter);
//
//        seeAllClubsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new FragmentClubs();
//                ((MainActivity) requireActivity()).replaceFragment(fragment, R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
//                // Update bottom navigation item
//                BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).findViewById(R.id.bottom_navigation_bar);
//                bottomNavigationView.setSelectedItemId(R.id.action_clubs);
//            }
//        });
//
//        ArrayList<UpcomingEventModel> upcomingEventModelArrayList = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            upcomingEventModelArrayList.add(new UpcomingEventModel(4.2F, "Event Name", upcomingEventsItems.get(i)));
//        }
//        UpcomingEventsFragmentHomeAdapter upcomingEventsFragmentHomeAdapter = new UpcomingEventsFragmentHomeAdapter(requireContext(), upcomingEventModelArrayList);
//        upcomingEventsGridView.setAdapter(upcomingEventsFragmentHomeAdapter);

//        seeAllUpcomingEventsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new FragmentEvents();
//                ((MainActivity) requireActivity()).replaceFragment(fragment, R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
//                // Update bottom navigation item
//                BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).findViewById(R.id.bottom_navigation_bar);
//                bottomNavigationView.setSelectedItemId(R.id.action_events);
//            }
//        });

        return rootView;
    }

    private void fetchData(EventDataListener listener) {
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
                    clubData = new Gson().fromJson(responseBody, JsonArray.class);
                    Log.d("unihivehome","club response : "+ clubData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("unihivehome","club failure : "+ String.valueOf(t));
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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