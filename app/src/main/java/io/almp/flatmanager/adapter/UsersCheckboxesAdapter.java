package io.almp.flatmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.List;

import io.almp.flatmanager.R;
import io.almp.flatmanager.model.User;

public class UsersCheckboxesAdapter extends BaseAdapter {
    List<User> usersList;
    private Activity mActivity;
    private LayoutInflater inflater;

    public UsersCheckboxesAdapter(Activity mActivity, List<User> usersList) {
        this.usersList = usersList;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        if(usersList == null){
            return 0;
        } else {
            return usersList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.users_chceckboxes_list_item, parent, false);
        }
        CheckBox checkBox = convertView.findViewById(R.id.user_checkbox);
        String userName = usersList.get(position).getName();
        checkBox.setText(userName);
        checkBox.setTag(position);

        return convertView;
    }
}
