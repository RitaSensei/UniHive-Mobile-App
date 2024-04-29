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

public class UpcomingEventsFragmentHomeAdapter extends ArrayAdapter<UpcomingEventModel> {
    // ViewHolder class to hold references to views
    private static class ViewHolder {
        TextView eventRating, eventTitle;
        ImageView eventPoster;
    }

    public UpcomingEventsFragmentHomeAdapter(@NonNull Context context, List<UpcomingEventModel> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UpcomingEventsFragmentHomeAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            // If convertView is null, inflate the layout and create a new ViewHolder
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_upcoming_event_card, parent, false);

            viewHolder = new UpcomingEventsFragmentHomeAdapter.ViewHolder();
            viewHolder.eventRating = convertView.findViewById(R.id.event_rating_card_item);
            viewHolder.eventTitle = convertView.findViewById(R.id.event_title_card_item);
            viewHolder.eventPoster = convertView.findViewById(R.id.event_poster_card_item);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, retrieve the ViewHolder from the tag
            viewHolder = (UpcomingEventsFragmentHomeAdapter.ViewHolder) convertView.getTag();
        }

        // Get the ClubModel object at the current position
        UpcomingEventModel upcomingEventModel = getItem(position);

        // Set data to views using ViewHolder references
//        viewHolder.clubRating.setText(clubModel != null ? clubModel.getClubRating() : "");
        viewHolder.eventPoster.setImageResource(upcomingEventModel != null ? upcomingEventModel.getImgId() : 0);

        return convertView;
    }
}
