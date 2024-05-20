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
import com.biog.unihiveandroid.model.Admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuperadminAdminsFragmentAdapter extends ArrayAdapter<Admin> {
    private static class ViewHolder {
        TextView adminFirstName, adminLastName;
        ImageButton editButton, deleteButton;
    }
    public SuperadminAdminsFragmentAdapter(@NonNull Context context, List<Admin> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SuperadminAdminsFragmentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_admin_card_grid_item, parent, false);

            viewHolder = new SuperadminAdminsFragmentAdapter.ViewHolder();
            viewHolder.adminFirstName = convertView.findViewById(R.id.admin_firstname_grid_item);
            viewHolder.adminLastName = convertView.findViewById(R.id.admin_lastname_grid_item);
            viewHolder.editButton = convertView.findViewById(R.id.admin_edit_button_grid_item);
            viewHolder.deleteButton = convertView.findViewById(R.id.admin_delete_button_grid_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuperadminAdminsFragmentAdapter.ViewHolder) convertView.getTag();
        }

        Admin object = getItem(position);
        if (object != null) {
            viewHolder.adminFirstName.setText(object.getFirstName());
            viewHolder.adminLastName.setText(object.getLastName());
        }
        return convertView;
    }
}
