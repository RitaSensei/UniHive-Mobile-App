package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Club;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.List;

public class ClubFragmentClubAdapter extends ArrayAdapter<Club> {
    // ViewHolder class to hold references to views
    private static class ViewHolder {
        TextView clubName,clubDescription, clubRatingValue;
        RatingBar clubRating;
        ShapeableImageView clubLogo;
    }

    public ClubFragmentClubAdapter(@NonNull Context context, List<Club> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // If convertView is null, inflate the layout and create a new ViewHolder
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_club_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.clubName = convertView.findViewById(R.id.club_name_list_view_item);
            viewHolder.clubDescription = convertView.findViewById(R.id.club_description_list_view_item);
            viewHolder.clubRating = convertView.findViewById(R.id.club_rating_list_item);
            viewHolder.clubRatingValue = convertView.findViewById(R.id.club_rating_value_list_item);
            viewHolder.clubLogo = convertView.findViewById(R.id.club_logo_list_view_item);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, retrieve the ViewHolder from the tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Club club = getItem(position);
        // Set data to views using ViewHolder references
        if (club != null) {
            viewHolder.clubName.setText(club.getClubName());
            float ratingValue = club.getClubRating();
            if (ratingValue == 0) {
                viewHolder.clubRatingValue.setTextColor(ContextCompat.getColor(getContext(), R.color.mid_grey));
            }
            viewHolder.clubRatingValue.setText(String.valueOf(ratingValue));
            viewHolder.clubRating.setRating(club.getClubRating());
            viewHolder.clubRating.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.yellow)));
            Glide.with(getContext())
                    .load(club.getClubLogo())
                    .into(viewHolder.clubLogo);
            String clubDescription = club.getClubDescription();
            int maxLength = 60;
            if (clubDescription.length() > maxLength) {
                clubDescription = clubDescription.substring(0, maxLength) + "...";
            }
            viewHolder.clubDescription.setText(clubDescription);
        }

        return convertView;
    }
}