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
import io.almp.flatmanager.model.ShoppingHistoryEntity;

public class ShoppingHistoryAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<ShoppingHistoryEntity> mHistories;
    private LayoutInflater inflater;

    public ShoppingHistoryAdapter(Activity fragment, List<ShoppingHistoryEntity> histories) {
        mActivity = fragment;
        mHistories = histories;
    }

    @Override
    public int getCount(){
        if (mHistories == null) {
            return 0;
        } else
            return this.mHistories.size();
    }

    @Override
    public Object getItem(int position){
        return this.mHistories.get(position);
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
            convertView = inflater.inflate(R.layout.shopping_history_list_item, parent, false);
        }

        TextView itemNameTextView = convertView.findViewById(R.id.s_item_name_text_view);
        itemNameTextView.setText(mHistories.get(position).getName());
        TextView itemPriceTextView = convertView.findViewById(R.id.s_price_text_view);
        double cost = mHistories.get(position).getCost();
        itemPriceTextView.setText(String.valueOf(cost));
        TextView buyerTextView = convertView.findViewById(R.id.s_buyer_text_view);
        buyerTextView.setText(mHistories.get(position).getBuyer());

        return convertView;
    }

}
