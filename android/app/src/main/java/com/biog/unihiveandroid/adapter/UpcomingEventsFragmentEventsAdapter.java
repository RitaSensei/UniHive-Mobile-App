package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.UpcomingEventModel;

import java.util.List;

public class UpcomingEventsFragmentEventsAdapter extends ArrayAdapter<UpcomingEventModel> {
    // ViewHolder class to hold references to views
    private static class ViewHolder {
        TextView eventRating, eventTitle, eventDate, clubName;
        ImageView eventPoster;
    }

    public UpcomingEventsFragmentEventsAdapter(@NonNull Context context, List<UpcomingEventModel> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UpcomingEventsFragmentEventsAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            // If convertView is null, inflate the layout and create a new ViewHolder
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_upcoming_event_list_item, parent, false);

            viewHolder = new UpcomingEventsFragmentEventsAdapter.ViewHolder();
            viewHolder.eventTitle = convertView.findViewById(R.id.event_title_list_view_item);
            viewHolder.eventDate = convertView.findViewById(R.id.event_date_list_view_item);
            viewHolder.clubName = convertView.findViewById(R.id.club_name_list_view_item);
            viewHolder.eventPoster = convertView.findViewById(R.id.event_poster_list_view_item);
            viewHolder.eventRating = convertView.findViewById(R.id.event_rating_list_view_item);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, retrieve the ViewHolder from the tag
            viewHolder = (UpcomingEventsFragmentEventsAdapter.ViewHolder) convertView.getTag();
        }

        // Get the UpcomingEventModel object at the current position
        UpcomingEventModel upcomingEventModel = getItem(position);

        // Set data to views using ViewHolder references
//        viewHolder.clubRating.setText(clubModel != null ? clubModel.getClubRating() : "");
        viewHolder.eventPoster.setImageResource(upcomingEventModel != null ? upcomingEventModel.getImgId() : 0);

        return convertView;
    }
}
