package com.biog.unihiveandroid.ui.events;

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

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.SettingsActivity;
import com.biog.unihiveandroid.ui.home.FragmentHome;

public class FragmentEvents extends Fragment {

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
        Toolbar toolbar = rootView.findViewById(R.id.events_toolbar);
        ImageButton settingsIcon = toolbar.findViewById(R.id.action_bar_events_settings_icon);

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent((Activity) getContext(), SettingsActivity.class));
            }
        });

        return rootView;
    }

}