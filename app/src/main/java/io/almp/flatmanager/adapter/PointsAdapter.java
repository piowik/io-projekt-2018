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

/**
 *  Class containing methods required to create the proper view for points.
 */

public class PointsAdapter extends BaseAdapter {
    private final Activity mActivity;
    private final List<User> mUserPoints;
    private double bestPerson;
    private double worstPerson;
    private LayoutInflater inflater;

    public PointsAdapter(Activity fragment, List<User> userPointsList) {
        mActivity = fragment;
        mUserPoints = userPointsList;
        bestPerson = mUserPoints.get(0).getPoints();
        worstPerson = mUserPoints.get(0).getPoints();
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
            convertView = inflater.inflate(R.layout.points_list_item, parent, false);
        }

        TextView userNameTextView = convertView.findViewById(R.id.user_name_text_view);
        userNameTextView.setText(mUserPoints.get(position).getName());
        userNameTextView.setTextColor(convertView.getResources().getColor(R.color.black));
        TextView userPointsTextView = convertView.findViewById(R.id.user_points_text_view);
        double points = mUserPoints.get(position).getPoints();
        userPointsTextView.setText(String.valueOf(points));

        for (User user : mUserPoints){
            if (bestPerson <= user.getPoints()){
                bestPerson = user.getPoints();
            }
            if(worstPerson >= user.getPoints()){
                worstPerson = user.getPoints();
            }
        }

        if(points == bestPerson){
            userPointsTextView.setTextColor(convertView.getResources().getColor(R.color.green));
        }
        else if(points == worstPerson) {
            userPointsTextView.setTextColor(convertView.getResources().getColor(R.color.red));
        }
        else {
            userPointsTextView.setTextColor(convertView.getResources().getColor(R.color.black));
        }

        return convertView;
    }
}
