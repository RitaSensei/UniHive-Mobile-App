package com.biog.unihiveandroid.ui.home;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.SettingsActivity;
import com.biog.unihiveandroid.ui.ImageAdapter;
import com.biog.unihiveandroid.ui.ImageViewActivity;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.FullScreenCarouselStrategy;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

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
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ImageView settingsIcon = toolbar.findViewById(R.id.action_bar_settings_icon);
        RecyclerView carouselRecyclerView = rootView.findViewById(R.id.carousel_recycler_view);
        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(new FullScreenCarouselStrategy());

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

        // Prepare data for the adapter
        ArrayList<Integer> carouselItems = new ArrayList<>();
        carouselItems.add(R.drawable.itholic_banner);
        carouselItems.add(R.drawable.aiday_banner);
        carouselItems.add(R.drawable.japanday_banner);
        carouselItems.add(R.drawable.cindhconvoi_banner);

        // Set up the adapter
        ImageAdapter adapter = new ImageAdapter(getContext(), carouselItems);
        carouselRecyclerView.setLayoutManager(carouselLayoutManager);
        carouselLayoutManager.setCarouselAlignment(CarouselLayoutManager.ALIGNMENT_CENTER);
        carouselRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                startActivity(new Intent(getContext(), ImageViewActivity.class).putExtra("image", path), ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), imageView, "image").toBundle());
            }
        });

        return rootView;
    }

}