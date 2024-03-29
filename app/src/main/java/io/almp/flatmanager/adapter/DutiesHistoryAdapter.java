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
import io.almp.flatmanager.model.DutiesHistoryEntity;

/**
 *  Class containing methods required to create the proper view for history of duties.
 */

public class DutiesHistoryAdapter extends BaseAdapter {
    private final Activity mActivity;
    private final List<DutiesHistoryEntity> mHistory;
    private LayoutInflater inflater;

    public DutiesHistoryAdapter(Activity fragment, List<DutiesHistoryEntity> history) {
        mActivity = fragment;
        mHistory = history;
    }

    @Override
    public int getCount(){
        if (mHistory == null) {
            return 0;
        } else
            return this.mHistory.size();
    }

    @Override
    public Object getItem(int position){
        return this.mHistory.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(inflater == null){
            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.duties_history_list, parent, false);
        }

        TextView itemNameTextView = convertView.findViewById(R.id.d_hist_name_text_view);
        String dutyName = mHistory.get(position).getDuty_name();
        itemNameTextView.setText(dutyName);
        TextView dateTextView = convertView.findViewById(R.id.d_hist_date_text_view);
        String dutyDate = mHistory.get(position).getCompletion_date();
        dateTextView.setText(dutyDate);

        return convertView;
    }
}
