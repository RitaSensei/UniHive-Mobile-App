package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.ClubModel;

import java.util.List;

public class ClubFragmentHomeAdapter extends ArrayAdapter<ClubModel> {
    // ViewHolder class to hold references to views
    private static class ViewHolder {
        TextView clubRating;
        ImageView clubLogo;
    }

    public ClubFragmentHomeAdapter(@NonNull Context context, List<ClubModel> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
        ClubModel clubModel = getItem(position);

        // Set data to views using ViewHolder references
//        viewHolder.clubRating.setText(clubModel != null ? clubModel.getClubRating() : "");
        viewHolder.clubLogo.setImageResource(clubModel != null ? clubModel.getImgId() : 0);

        return convertView;
    }
}

