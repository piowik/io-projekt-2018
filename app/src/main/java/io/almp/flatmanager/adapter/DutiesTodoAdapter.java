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
import io.almp.flatmanager.model.DutiesEntity;

/**
 *  Class containing methods required to create the proper view for duties to do.
 */

public class DutiesTodoAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<DutiesEntity> mTodo;
    private LayoutInflater inflater;

    public DutiesTodoAdapter(Activity fragment, List<DutiesEntity> histories) {
        mActivity = fragment;
        mTodo = histories;
    }

    @Override
    public int getCount(){
        if (mTodo == null) {
            return 0;
        } else
            return this.mTodo.size();
    }

    @Override
    public Object getItem(int position){
        return this.mTodo.get(position);
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
            convertView = inflater.inflate(R.layout.duties_todo_list, parent, false);
        }

        TextView itemNameTextView = convertView.findViewById(R.id.d_todo_name_text_view);
        String dutyName = mTodo.get(position).getDuty_name();
        itemNameTextView.setText(dutyName);
        TextView valueTextView = convertView.findViewById(R.id.d_todo_value_text_view);
        String dutyValue = mTodo.get(position).getValue();
        valueTextView.setText(dutyValue);

        return convertView;
    }

}
