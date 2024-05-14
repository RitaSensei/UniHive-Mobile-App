package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Event;
import com.biog.unihiveandroid.model.Month;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class UpcomingEventsFragmentEventsAdapter extends ArrayAdapter<Event> {
    // ViewHolder class to hold references to views
    private static class ViewHolder {
        TextView eventRatingValue, eventTitle, eventDate, clubName;
        RatingBar eventRating;
        ShapeableImageView eventPoster;
    }

    public UpcomingEventsFragmentEventsAdapter(@NonNull Context context, List<Event> objects) {
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
            viewHolder.eventRating = convertView.findViewById(R.id.event_rating_list_item);
            viewHolder.eventRatingValue = convertView.findViewById(R.id.event_rating_value_list_item);
            viewHolder.clubName = convertView.findViewById(R.id.club_name_list_view_item);
            viewHolder.eventPoster = convertView.findViewById(R.id.event_poster_list_view_item);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, retrieve the ViewHolder from the tag
            viewHolder = (UpcomingEventsFragmentEventsAdapter.ViewHolder) convertView.getTag();
        }
        Event event = getItem(position);
        if (event != null) {
            String eventName = event.getEventName();
            if (eventName.length() > 36) {
                eventName = eventName.substring(0, 36) + "...";
            }
            viewHolder.eventTitle.setText(eventName);
            Instant startDate = event.getStartTime();
            Instant endDate = event.getEndTime();
            LocalDate start = startDate.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = endDate.atZone(ZoneId.systemDefault()).toLocalDate();
            int startDay = start.getDayOfMonth();
            int endDay = end.getDayOfMonth();
            Month startMonth = Month.valueOf(start.getMonth().name());
            Month endMonth = Month.valueOf(end.getMonth().name());
            int startYear = start.getYear();
            int endYear = end.getYear();
            String fullDate = "From " + startDay + " " + startMonth.getMonthName() + " " + startYear +
                    " To " + endDay + " " + endMonth.getMonthName() + " " + endYear;
            viewHolder.eventDate.setText(fullDate);
            viewHolder.eventRating.setRating(event.getEventRating());
            viewHolder.eventRating.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.yellow)));
            float ratingValue = event.getEventRating();
            if (ratingValue == 0) {
                viewHolder.eventRatingValue.setTextColor(ContextCompat.getColor(getContext(), R.color.mid_grey));
            }
            viewHolder.eventRatingValue.setText(String.valueOf(ratingValue));
            viewHolder.clubName.setText(event.getClub().getClubName());
            Glide.with(getContext())
                    .load(event.getEventBanner())
                    .into(viewHolder.eventPoster);
        }

        return convertView;
    }
}
