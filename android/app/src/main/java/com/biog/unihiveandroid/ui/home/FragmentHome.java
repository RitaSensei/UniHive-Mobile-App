package com.biog.unihiveandroid.ui.home;

import static com.biog.unihiveandroid.ImageData.getClubsGridItems;
import static com.biog.unihiveandroid.ImageData.getTrendingEventsSwitcherItems;
import static com.biog.unihiveandroid.ImageData.getUpcomingEventsGridItems;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.biog.unihiveandroid.adapter.ClubAdapter;
import com.biog.unihiveandroid.adapter.UpcomingEventAdapter;
import com.biog.unihiveandroid.model.ClubModel;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.SettingsActivity;
import com.biog.unihiveandroid.model.UpcomingEventModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    int currentPosition = 0;
    List<Integer> trendingEventsItems = getTrendingEventsSwitcherItems();
    List<Integer> clubsItems = getClubsGridItems();
    List<Integer> upcomingEventsItems = getUpcomingEventsGridItems();
    GridView clubsGridView, upcomingEventsGridView;

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
        Toolbar toolbar = rootView.findViewById(R.id.home_toolbar);
        ImageButton settingsIcon = toolbar.findViewById(R.id.action_bar_home_settings_icon);

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent((Activity) getContext(), SettingsActivity.class));
            }
        });

        ImageSwitcher trendingEventsSwitcher = rootView.findViewById(R.id.trending_events_switcher_home);
        ImageButton previousButton = rootView.findViewById(R.id.previous_button_switcher);
        ImageButton nextButton = rootView.findViewById(R.id.next_button_switcher);
        int trendingEventsCount = trendingEventsItems.size();

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
        trendingEventsSwitcher.setImageResource(trendingEventsItems.get(currentPosition));

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition--;
                if (currentPosition < 0) {
                    currentPosition = trendingEventsItems.size() - 1;
                }
                trendingEventsSwitcher.setImageResource(trendingEventsItems.get(currentPosition));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition++;
                if (currentPosition == trendingEventsCount) {
                    currentPosition = 0;
                }
                trendingEventsSwitcher.setImageResource(trendingEventsItems.get(currentPosition));
            }
        });

        clubsGridView = rootView.findViewById(R.id.clubs_grid_view);
        ArrayList<ClubModel> clubModelArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            clubModelArrayList.add(new ClubModel(5, clubsItems.get(i)));
        }
        ClubAdapter clubAdapter = new ClubAdapter(requireContext(), clubModelArrayList);
        clubsGridView.setAdapter(clubAdapter);

        ImageButton seeAllClubsButton = rootView.findViewById(R.id.see_all_button_clubs_switcher);


        upcomingEventsGridView = rootView.findViewById(R.id.upcoming_events_grid_view);
        ArrayList<UpcomingEventModel> upcomingEventModelArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            upcomingEventModelArrayList.add(new UpcomingEventModel(5, "Event Name", upcomingEventsItems.get(i)));
        }
        UpcomingEventAdapter upcomingEventAdapter = new UpcomingEventAdapter(requireContext(), upcomingEventModelArrayList);
        upcomingEventsGridView.setAdapter(upcomingEventAdapter);

        ImageButton seeAllUpcomingEventsButton = rootView.findViewById(R.id.see_all_button_upcoming_events_switcher);


        return rootView;
    }
}