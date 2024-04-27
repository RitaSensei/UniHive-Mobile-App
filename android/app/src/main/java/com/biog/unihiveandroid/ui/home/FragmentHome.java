package com.biog.unihiveandroid.ui.home;

import static com.biog.unihiveandroid.ImageData.getSwitcherItems;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.biog.unihiveandroid.ImageData;
import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.SettingsActivity;
import com.biog.unihiveandroid.authentication.LoginActivity;
import com.biog.unihiveandroid.authentication.SignupActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    int currentPosition = 0;
    List<Integer> switcherItems = getSwitcherItems();

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

        ImageSwitcher imageSwitcher = rootView.findViewById(R.id.image_switcher);
        ImageButton previousButton = rootView.findViewById(R.id.previous_button_switcher);
        ImageButton nextButton = rootView.findViewById(R.id.next_button_switcher);
        int count = switcherItems.size();

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getContext());
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });

        imageSwitcher.setImageResource(switcherItems.get(currentPosition));

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition--;
                if (currentPosition < 0) {
                    currentPosition = switcherItems.size() - 1;
                }
                imageSwitcher.setImageResource(switcherItems.get(currentPosition));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition++;
                if (currentPosition == count) {
                    currentPosition = 0;
                }
                imageSwitcher.setImageResource(switcherItems.get(currentPosition));
            }
        });
        return rootView;
    }

    private void adjustImageSize(ImageView imageView, int imageResource) {
        // Get the dimensions of the ImageView
        int targetWidth = imageView.getWidth();
        int targetHeight = imageView.getHeight();

        // Get the dimensions of the original image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), imageResource, options);
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        // Calculate the scaling factors
        float widthScaleFactor = (float) targetWidth / originalWidth;
        float heightScaleFactor = (float) targetHeight / originalHeight;

        // Take the minimum scaling factor
        float scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);

        // Calculate the new dimensions
        int newWidth = Math.round(originalWidth * scaleFactor);
        int newHeight = Math.round(originalHeight * scaleFactor);

        // Set the new dimensions to the ImageView
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = newWidth;
        params.height = newHeight;
        imageView.setLayoutParams(params);
    }

}