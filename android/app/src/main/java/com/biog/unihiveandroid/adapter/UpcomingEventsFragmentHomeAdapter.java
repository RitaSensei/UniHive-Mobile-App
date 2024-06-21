package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Event;
import com.biog.unihiveandroid.model.UpcomingEventModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class UpcomingEventsFragmentHomeAdapter extends ArrayAdapter<Event> {
    // ViewHolder class to hold references to views
    private static class ViewHolder {
        TextView eventTitle, eventRatingNumber;
        RatingBar eventRating;
        ImageView eventPoster;
    }

    public UpcomingEventsFragmentHomeAdapter(@NonNull Context context, List<Event> objects) {
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
            viewHolder.eventRatingNumber = convertView.findViewById(R.id.event_rating_number_card_item);
            viewHolder.eventTitle = convertView.findViewById(R.id.event_title_card_item);
            viewHolder.eventPoster = convertView.findViewById(R.id.event_poster_card_item);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, retrieve the ViewHolder from the tag
            viewHolder = (UpcomingEventsFragmentHomeAdapter.ViewHolder) convertView.getTag();
        }
        Event event = getItem(position);

        // Set data to views using ViewHolder references
        if (event != null) {
            viewHolder.eventRating.setRating(event.getEventRating());
            viewHolder.eventRating.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.yellow)));
            viewHolder.eventRatingNumber.setText(String.valueOf(event.getEventRating()));
            Glide.with(getContext())
                    .load(event.getEventBanner())
                    .into(viewHolder.eventPoster);
            // Truncate the title if it's longer than a certain length
            String eventTitle = event.getEventName();
            int maxLength = 30;
            if (eventTitle.length() > maxLength) {
                eventTitle = eventTitle.substring(0, maxLength) + "...";
            }
            viewHolder.eventTitle.setText(eventTitle);
        }
        return convertView;
    }
}
