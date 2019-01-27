package io.almp.flatmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import io.almp.flatmanager.R;
import io.almp.flatmanager.Utils;
import io.almp.flatmanager.model.User;

/**
 *  Class containing methods required to create the proper view for checkboxes of users used in shopping tab.
 */

public class UserRentAdapter extends BaseAdapter {
    private final List<User> mUsersList;
    private final Activity mActivity;
    private LayoutInflater inflater;
    private RentCallbackInterface mRentCallbackInterface;

    public UserRentAdapter(Activity mActivity, List<User> usersList, RentCallbackInterface rentCallbackInterface) {
        this.mUsersList = usersList;
        this.mActivity = mActivity;
        this.mRentCallbackInterface = rentCallbackInterface;
    }

    @Override
    public int getCount() {
        return mUsersList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsersList.get(position);
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
            convertView = inflater.inflate(R.layout.rent_per_user_list_item, parent, false);
        }
        String userName = Utils.nameParser(mUsersList.get(position).getName());
        TextView userNameTextView = convertView.findViewById(R.id.user_name_text_view);
        userNameTextView.setText(userName);
        EditText editText = convertView.findViewById(R.id.rentValueEditText);
        editText.setTag(mUsersList.get(position).getId());
        editText.setOnFocusChangeListener((view, b) -> mRentCallbackInterface.onRentChange());
        return convertView;
    }
}
