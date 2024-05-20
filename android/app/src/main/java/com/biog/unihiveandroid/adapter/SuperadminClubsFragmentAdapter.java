package com.biog.unihiveandroid.adapter;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Admin;
import com.biog.unihiveandroid.model.Club;
import com.biog.unihiveandroid.model.School;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SuperadminClubsFragmentAdapter extends ArrayAdapter<Club> {
    private static class ViewHolder {
        TextView clubName, clubSchoolName;
        ImageButton editButton, deleteButton;
    }
    public SuperadminClubsFragmentAdapter(@NonNull Context context, List<Club> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SuperadminClubsFragmentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_club_card_grid_item, parent, false);

            viewHolder = new SuperadminClubsFragmentAdapter.ViewHolder();
            viewHolder.clubName = convertView.findViewById(R.id.club_name_grid_item);
            viewHolder.clubSchoolName = convertView.findViewById(R.id.club_school_grid_item);
            viewHolder.editButton = convertView.findViewById(R.id.club_edit_button_grid_item);
            viewHolder.deleteButton = convertView.findViewById(R.id.club_delete_button_grid_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuperadminClubsFragmentAdapter.ViewHolder) convertView.getTag();
        }

        Club object = getItem(position);
        if (object != null) {
            viewHolder.clubName.setText(object.getClubName());
            School school = object.getSchool();
            viewHolder.clubSchoolName.setText(school.getSchoolName());
        }
        return convertView;
    }
}
