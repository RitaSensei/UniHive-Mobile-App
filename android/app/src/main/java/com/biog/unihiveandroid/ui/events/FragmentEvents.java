package com.biog.unihiveandroid.ui.events;

import static com.biog.unihiveandroid.ImageData.getUpcomingEventsGridItems;
import static java.time.Instant.now;

import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.adapter.UpcomingEventsFragmentEventsAdapter;
import com.biog.unihiveandroid.model.UpcomingEventModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.Instant;

public class FragmentEvents extends Fragment {
    List<Integer> upcomingEventsItems = getUpcomingEventsGridItems();
    ListView upcomingEventsListView;

    public FragmentEvents() {
        // Required empty public constructor
    }

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

        // Access the textview in Toolbar and change its text
        TextView toolbarTitle =((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_title);
        toolbarTitle.setText(R.string.ic_events);
        // Access the search icon in Toolbar and change its visibility
        ImageButton searchIcon = ((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_search_icon);
        searchIcon.setVisibility(View.VISIBLE);

        upcomingEventsListView = rootView.findViewById(R.id.upcoming_events_list_view);
        ArrayList<UpcomingEventModel> upcomingEventModelArrayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            upcomingEventModelArrayList.add(new UpcomingEventModel("Event Name", now(), 4.5F, "Club Name" , upcomingEventsItems.get(i)));
        }
        UpcomingEventsFragmentEventsAdapter upcomingEventsFragmentEventsAdapter = new UpcomingEventsFragmentEventsAdapter(requireContext(), upcomingEventModelArrayList);
        upcomingEventsListView.setAdapter(upcomingEventsFragmentEventsAdapter);

//        ImageButton seeAllUpcomingEventsButton = rootView.findViewById(R.id.see_all_button_upcoming_events_switcher);
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

}