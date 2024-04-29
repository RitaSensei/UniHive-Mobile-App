package com.biog.unihiveandroid.ui.home;

import static com.biog.unihiveandroid.ImageData.getClubsGridItems;
import static com.biog.unihiveandroid.ImageData.getTrendingEventsSwitcherItems;
import static com.biog.unihiveandroid.ImageData.getUpcomingEventsGridItems;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.adapter.ClubFragmentHomeAdapter;
import com.biog.unihiveandroid.adapter.UpcomingEventsFragmentHomeAdapter;
import com.biog.unihiveandroid.model.ClubModel;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.UpcomingEventModel;
import com.biog.unihiveandroid.ui.clubs.FragmentClubs;
import com.biog.unihiveandroid.ui.events.FragmentEvents;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        // Access the textview in Toolbar and change its text
        TextView toolbarTitle =((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_title);
        toolbarTitle.setText(R.string.ic_home);

        // Access the search icon in Toolbar and change its visibility
        ImageButton searchIcon = ((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_search_icon);
        searchIcon.setVisibility(View.GONE);

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
        trendingEventsSwitcher.setInAnimation(requireActivity(), R.anim.slide_in_animation);
        trendingEventsSwitcher.setOutAnimation(requireActivity(), R.anim.slide_out_animation);

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
            clubModelArrayList.add(new ClubModel(5.0F, clubsItems.get(i)));
        }
        ClubFragmentHomeAdapter clubFragmentHomeAdapter = new ClubFragmentHomeAdapter(requireContext(), clubModelArrayList);
        clubsGridView.setAdapter(clubFragmentHomeAdapter);

        ImageButton seeAllClubsButton = rootView.findViewById(R.id.see_all_button_clubs_switcher);
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

        upcomingEventsGridView = rootView.findViewById(R.id.upcoming_events_grid_view);
        ArrayList<UpcomingEventModel> upcomingEventModelArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            upcomingEventModelArrayList.add(new UpcomingEventModel(4.2F, "Event Name", upcomingEventsItems.get(i)));
        }
        UpcomingEventsFragmentHomeAdapter upcomingEventsFragmentHomeAdapter = new UpcomingEventsFragmentHomeAdapter(requireContext(), upcomingEventModelArrayList);
        upcomingEventsGridView.setAdapter(upcomingEventsFragmentHomeAdapter);

        ImageButton seeAllUpcomingEventsButton = rootView.findViewById(R.id.see_all_button_upcoming_events_switcher);
        seeAllUpcomingEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentEvents();
                ((MainActivity) requireActivity()).replaceFragment(fragment, R.anim.slide_in_animation, R.anim.slide_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation);
                // Update bottom navigation item
                BottomNavigationView bottomNavigationView = ((MainActivity) requireActivity()).findViewById(R.id.bottom_navigation_bar);
                bottomNavigationView.setSelectedItemId(R.id.action_events);
            }
        });


        return rootView;
    }
}