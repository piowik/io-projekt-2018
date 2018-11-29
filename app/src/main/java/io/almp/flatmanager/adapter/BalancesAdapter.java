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
import io.almp.flatmanager.model.User;

public class BalancesAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<User> mUserBalances;
    private LayoutInflater inflater;

    public BalancesAdapter(Activity fragment, List<User> userBalancesList) {
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
        userNameTextView.setText(mUserBalances.get(position).getName());
        userNameTextView.setTextColor(convertView.getResources().getColor(R.color.black));
        TextView userBalanceTextView = convertView.findViewById(R.id.user_balance_text_view);
        double balance = mUserBalances.get(position).getBalance();
        if(balance >= 0){
            userBalanceTextView.setText(new StringBuilder().append("+").append(String.valueOf(balance)).toString());
            userBalanceTextView.setTextColor(convertView.getResources().getColor(R.color.green));
        } else {
            userBalanceTextView.setText(String.valueOf(balance));
            userBalanceTextView.setTextColor(convertView.getResources().getColor(R.color.red));
        }

        return convertView;
    }
}
