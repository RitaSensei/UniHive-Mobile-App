package com.biog.unihiveandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.biog.unihiveandroid.R;

import java.util.List;

public class SuperadminDashboardFragmentAdapter extends ArrayAdapter<Object> {
    private static class ViewHolder {
        TextView tableName, numberOfRows;
    }
        public SuperadminDashboardFragmentAdapter(@NonNull Context context, List<Object> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SuperadminDashboardFragmentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_grid_item, parent, false);
            viewHolder = new SuperadminDashboardFragmentAdapter.ViewHolder();
            viewHolder.tableName = convertView.findViewById(R.id.table_name_item);
            viewHolder.numberOfRows = convertView.findViewById(R.id.club_name_list_view_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuperadminDashboardFragmentAdapter.ViewHolder) convertView.getTag();
        }
        Object object = getItem(position);
        assert object != null;
        viewHolder.tableName.setText(object.toString());
        viewHolder.numberOfRows.setText("4 rows");
        return convertView;
    }
}
