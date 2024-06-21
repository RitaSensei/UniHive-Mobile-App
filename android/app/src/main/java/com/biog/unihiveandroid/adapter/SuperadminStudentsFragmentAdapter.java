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
import com.biog.unihiveandroid.model.School;
import com.biog.unihiveandroid.model.Student;

import java.util.List;

public class SuperadminStudentsFragmentAdapter extends ArrayAdapter<Student> {
    private static class ViewHolder {
        TextView studentFirstname, studentLastname;
        ImageButton editButton, deleteButton;
    }
    public SuperadminStudentsFragmentAdapter(@NonNull Context context, List<Student> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SuperadminStudentsFragmentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_student_card_grid_item, parent, false);

            viewHolder = new SuperadminStudentsFragmentAdapter.ViewHolder();
            viewHolder.studentFirstname = convertView.findViewById(R.id.student_firstname_grid_item);
            viewHolder.studentLastname = convertView.findViewById(R.id.student_lastname_grid_item);
            viewHolder.editButton = convertView.findViewById(R.id.student_edit_button_grid_item);
            viewHolder.deleteButton = convertView.findViewById(R.id.student_delete_button_grid_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuperadminStudentsFragmentAdapter.ViewHolder) convertView.getTag();
        }

        Student object = getItem(position);
        if (object != null) {
            viewHolder.studentFirstname.setText(object.getFirstName());
            viewHolder.studentLastname.setText(object.getLastName());
        }
        return convertView;
    }
}
