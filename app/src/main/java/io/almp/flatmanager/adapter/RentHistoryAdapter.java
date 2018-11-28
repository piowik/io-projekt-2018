package io.almp.flatmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.almp.flatmanager.R;
import io.almp.flatmanager.model.RentHistory;

public class RentHistoryAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<RentHistory> mItems;
    private LayoutInflater mInflater;

    public RentHistoryAdapter(Activity activity, List<RentHistory> items) {
        this.mActivity = activity;
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return this.mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (mInflater == null)
            mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.rent_list_item, parent, false);

        RentHistory rentItem = mItems.get(position);

        TextView dateTextView = convertView.findViewById(R.id.rent_date_text_view);
        dateTextView.setText(rentItem.getDate());

        TextView valueTextView = convertView.findViewById(R.id.rent_value_text_view);
        valueTextView.setText(String.valueOf(rentItem.getValue()));

        return convertView;
    }
}
