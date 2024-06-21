package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Event;

import java.util.List;

public class SuperadminEventsFragmentAdapter extends ArrayAdapter<Event> {
    private static class ViewHolder {
        TextView eventName;
        ImageButton editButton, deleteButton;
    }
    public SuperadminEventsFragmentAdapter(@NonNull Context context, List<Event> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SuperadminEventsFragmentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_event_card_grid_item, parent, false);

            viewHolder = new SuperadminEventsFragmentAdapter.ViewHolder();
            viewHolder.eventName = convertView.findViewById(R.id.event_name_grid_item);
            viewHolder.editButton = convertView.findViewById(R.id.event_edit_button_grid_item);
            viewHolder.deleteButton = convertView.findViewById(R.id.event_delete_button_grid_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuperadminEventsFragmentAdapter.ViewHolder) convertView.getTag();
        }

        Event object = getItem(position);
        if (object != null) {
            String eventName = object.getEventName();
            if (eventName.length() > 40) {
                eventName = eventName.substring(0, 20) + "...";
            }
            viewHolder.eventName.setText(eventName);
        }
        return convertView;
    }
}
