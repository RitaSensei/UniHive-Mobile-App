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
import com.biog.unihiveandroid.model.Club;
import com.biog.unihiveandroid.model.School;

import java.util.List;

public class SuperadminSchoolsFragmentAdapter extends ArrayAdapter<School> {
    private static class ViewHolder {
        TextView schoolName, schoolCity;
        ImageButton editButton, deleteButton;
    }
    public SuperadminSchoolsFragmentAdapter(@NonNull Context context, List<School> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SuperadminSchoolsFragmentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_school_card_grid_item, parent, false);

            viewHolder = new SuperadminSchoolsFragmentAdapter.ViewHolder();
            viewHolder.schoolName = convertView.findViewById(R.id.school_name_grid_item);
            viewHolder.schoolCity = convertView.findViewById(R.id.school_city_grid_item);
            viewHolder.editButton = convertView.findViewById(R.id.school_edit_button_grid_item);
            viewHolder.deleteButton = convertView.findViewById(R.id.school_delete_button_grid_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuperadminSchoolsFragmentAdapter.ViewHolder) convertView.getTag();
        }

        School object = getItem(position);
        if (object != null) {
            viewHolder.schoolName.setText(object.getSchoolName());
            viewHolder.schoolCity.setText(object.getSchoolCity());
        }
        return convertView;
    }
}
