package io.almp.flatmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.almp.flatmanager.R;
import io.almp.flatmanager.model.RentHistoryItem;

/**
 *  Class containing methods required to create the proper view for rent history.
 */

public class RentHistoryAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<RentHistoryItem> mItems;
    private LayoutInflater mInflater;

    public RentHistoryAdapter(Activity activity, List<RentHistoryItem> items) {
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

        RentHistoryItem rentItem = mItems.get(position);

        TextView dateTextView = convertView.findViewById(R.id.rent_date_text_view);
        dateTextView.setText(rentItem.getDate());

        TextView totalValueTextView = convertView.findViewById(R.id.rent_total_value_text_view);
        totalValueTextView.setText(String.valueOf(rentItem.getTotalValue()) + " złotych polskich");
        TextView valuePerPersonTextView = convertView.findViewById(R.id.rent_value_per_person_text_view);
        valuePerPersonTextView.setText(String.valueOf(rentItem.getValuePerPerson()) + " na głowę");

        return convertView;
    }
}
