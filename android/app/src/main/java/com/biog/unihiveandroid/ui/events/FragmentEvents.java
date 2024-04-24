package com.biog.unihiveandroid.ui.events;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEvents#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEvents extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentEvents() {
        // Required empty public constructor
    }

    public static com.biog.unihiveandroid.ui.events.FragmentEvents newInstance(String param1, String param2) {
        com.biog.unihiveandroid.ui.events.FragmentEvents fragment = new com.biog.unihiveandroid.ui.events.FragmentEvents();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ImageView settingsIcon = toolbar.findViewById(R.id.action_bar_settings_icon);

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        // Handle Up navigation (back button)
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Up navigation here, if needed
            }
        });

        return rootView;
    }

}