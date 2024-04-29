package com.biog.unihiveandroid.ui.clubs;

import static com.biog.unihiveandroid.ImageData.getClubsGridItems;
import static com.biog.unihiveandroid.ImageData.getUpcomingEventsGridItems;
import static java.time.Instant.now;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.biog.unihiveandroid.MainActivity;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.SettingsActivity;
import com.biog.unihiveandroid.adapter.ClubFragmentClubAdapter;
import com.biog.unihiveandroid.adapter.UpcomingEventsFragmentEventsAdapter;
import com.biog.unihiveandroid.model.ClubModel;
import com.biog.unihiveandroid.model.UpcomingEventModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentClubs extends Fragment {
    List<Integer> clubsItems = getClubsGridItems();
    ListView clubsListView;

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
        // Access the textview in Toolbar and change its text
        TextView toolbarTitle =((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_title);
        toolbarTitle.setText(R.string.ic_clubs);
        // Access the search icon in Toolbar and change its visibility
        ImageButton searchIcon = ((MainActivity) requireActivity()).findViewById(R.id.main_toolbar_search_icon);
        searchIcon.setVisibility(View.VISIBLE);

        clubsListView = rootView.findViewById(R.id.clubs_list_view);
        ArrayList<ClubModel> clubsModelArrayList = new ArrayList<>();
        for (int i = 0; i < clubsItems.size(); i++) {
            clubsModelArrayList.add(new ClubModel("Club Name", "Description of the club", 4.5F, clubsItems.get(i)));
        }
        ClubFragmentClubAdapter clubsFragmentClubsAdapter = new ClubFragmentClubAdapter(requireContext(), clubsModelArrayList);
        clubsListView.setAdapter(clubsFragmentClubsAdapter);

        return rootView;
    }
}