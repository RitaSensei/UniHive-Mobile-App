package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Club;
import com.bumptech.glide.Glide;

import java.util.List;

public class ClubFragmentHomeAdapter extends ArrayAdapter<Club> {
    // ViewHolder class to hold references to views
    private static class ViewHolder {
        RatingBar clubRating;
        ImageView clubLogo;
    }

    public ClubFragmentHomeAdapter(@NonNull Context context, List<Club> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // If convertView is null, inflate the layout and create a new ViewHolder
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_club_card, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.clubRating = convertView.findViewById(R.id.club_rating_card_item);
            viewHolder.clubLogo = convertView.findViewById(R.id.club_logo_card_item);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, retrieve the ViewHolder from the tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the ClubModel object at the current position
        Club club = getItem(position);

        // Set data to views using ViewHolder references
        if (club != null) {
            viewHolder.clubRating.setRating(club.getClubRating());
            viewHolder.clubRating.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.yellow)));
            Glide.with(getContext())
                    .load(club.getClubLogo())
                    .into(viewHolder.clubLogo);
        }

        return convertView;
    }
}