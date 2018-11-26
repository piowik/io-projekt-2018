package io.almp.flatmanager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BalancesAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<UserBalance> mUserBalances;
    private LayoutInflater inflater;

    public BalancesAdapter(Activity fragment, List<UserBalance> userBalancesList) {
        mActivity = fragment;
        mUserBalances = userBalancesList;
    }

    @Override
    public int getCount(){
        if (mUserBalances == null) {
            return 0;
        } else
            return this.mUserBalances.size();
    }

    @Override
    public Object getItem(int position){
        return this.mUserBalances.get(position);
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
            convertView = inflater.inflate(R.layout.balances_list_item, parent, false);
        }

        TextView userNameTextView = convertView.findViewById(R.id.user_name_text_view);
        userNameTextView.setText(mUserBalances.get(position).getUserName());
        TextView userBalanceTextView = convertView.findViewById(R.id.user_balance_text_view);
        userBalanceTextView.setText(String.valueOf(mUserBalances.get(position).getBalance()));

        return convertView;
    }
}
