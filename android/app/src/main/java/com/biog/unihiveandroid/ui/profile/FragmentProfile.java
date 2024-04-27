package com.biog.unihiveandroid.ui.profile;

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

public class FragmentProfile extends Fragment {

    public FragmentProfile() {
        // Required empty public constructor
    }

    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.profile_toolbar);
        ImageButton settingsIcon = toolbar.findViewById(R.id.action_bar_profile_settings_icon);

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent((Activity) getContext(), SettingsActivity.class));
            }
        });

        return rootView;
    }
}