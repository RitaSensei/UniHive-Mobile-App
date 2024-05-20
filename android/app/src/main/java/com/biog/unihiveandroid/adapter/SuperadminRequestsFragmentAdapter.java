package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.biog.unihiveandroid.R;
import com.biog.unihiveandroid.model.Month;
import com.biog.unihiveandroid.model.Request;
import com.biog.unihiveandroid.model.Student;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class SuperadminRequestsFragmentAdapter extends ArrayAdapter<Request> {
    private static class ViewHolder {
        TextView requestsStudentFirstname, requestsStudentLastname, requestDate;
        ImageButton editButton, deleteButton;
    }
    public SuperadminRequestsFragmentAdapter(@NonNull Context context, List<Request> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SuperadminRequestsFragmentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_request_card_grid_item, parent, false);

            viewHolder = new SuperadminRequestsFragmentAdapter.ViewHolder();
            viewHolder.requestsStudentFirstname = convertView.findViewById(R.id.request_studentFirstname_grid_item);
            viewHolder.requestsStudentLastname = convertView.findViewById(R.id.request_studentLastname_grid_item);
            viewHolder.requestDate = convertView.findViewById(R.id.request_date_grid_item);
            viewHolder.editButton = convertView.findViewById(R.id.request_edit_button_grid_item);
            viewHolder.deleteButton = convertView.findViewById(R.id.request_delete_button_grid_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuperadminRequestsFragmentAdapter.ViewHolder) convertView.getTag();
        }

        Request object = getItem(position);
        if (object != null) {
            viewHolder.requestsStudentFirstname.setText(object.getFirstName());
            viewHolder.requestsStudentLastname.setText(object.getLastName());
            Instant requestCreateDate = object.getCreatedAt();
            LocalDate start = requestCreateDate.atZone(ZoneId.systemDefault()).toLocalDate();
            int startDay = start.getDayOfMonth();
            int startMonth = start.getMonthValue();
            int startYear = start.getYear();
            String fullDate = startDay + "/" + startMonth + "/" + startYear;
            viewHolder.requestDate.setText(fullDate);
        }
        return convertView;
    }
}
