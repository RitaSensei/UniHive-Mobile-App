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
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ClubFragmentClubAdapter extends ArrayAdapter<ClubModel> {
    // ViewHolder class to hold references to views
    private static class ViewHolder {
        TextView clubName,clubDescription, clubRating;
        ShapeableImageView clubLogo;
    }

    public ClubFragmentClubAdapter(@NonNull Context context, List<ClubModel> objects) {
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
            viewHolder.clubRating = convertView.findViewById(R.id.club_rating_list_view_item);
            viewHolder.clubLogo = convertView.findViewById(R.id.club_logo_list_view_item);

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

