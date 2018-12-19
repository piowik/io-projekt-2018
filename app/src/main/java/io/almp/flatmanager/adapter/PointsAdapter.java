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

public class PointsAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<User> mUserPoints;
    private LayoutInflater inflater;

    public PointsAdapter(Activity fragment, List<User> userPointsList) {
        mActivity = fragment;
        mUserPoints = userPointsList;
    }

    @Override
    public int getCount(){
        if (mUserPoints == null) {
            return 0;
        } else
            return this.mUserPoints.size();
    }

    @Override
    public Object getItem(int position){
        return this.mUserPoints.get(position);
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
          //  convertView = inflater.inflate(R.layout.points_list_item, parent, false);
        }

        TextView userNameTextView = convertView.findViewById(R.id.user_name_text_view);
        userNameTextView.setText(mUserPoints.get(position).getName());
        userNameTextView.setTextColor(convertView.getResources().getColor(R.color.black));
       // TextView userPointsTextView = convertView.findViewById(R.id.user_points_text_view);
        double points = mUserPoints.get(position).getPoints();
        if(points >= 0){
        //    userPointsTextView.setText(new StringBuilder().append("+").append(String.valueOf(points)).toString());
        //    userPointsTextView.setTextColor(convertView.getResources().getColor(R.color.green));
        } else {
        //    userPointsTextView.setText(String.valueOf(points));
        //    userPointsTextView.setTextColor(convertView.getResources().getColor(R.color.red));
        }

        return convertView;
    }
}
