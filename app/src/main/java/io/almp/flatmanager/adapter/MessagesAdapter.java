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
import io.almp.flatmanager.model.Message;
import io.almp.flatmanager.model.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mateusz Zaremba on 29.11.2018.
 */
public class MessagesAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<Message> mMessagesList;
    private LayoutInflater inflater;

    public MessagesAdapter(Activity activity, List<Message> messagesList) {
        mActivity = activity;
        mMessagesList = messagesList;
    }

    @Override
    public int getCount(){
        if (mMessagesList == null) {
            return 0;
        } else
            return this.mMessagesList.size();
    }

    @Override
    public Object getItem(int position){
        return this.mMessagesList.get(position);
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
        Long myId = mActivity.getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
        TextView message;
        if(convertView == null){
            if(mMessagesList.get(position).getSenderId()==myId) {
                convertView = inflater.inflate(R.layout.message_item_a, parent, false);
                message = convertView.findViewById(R.id.message_a_textview);
            }else {
                convertView = inflater.inflate(R.layout.message_item_b, parent, false);
                message = convertView.findViewById(R.id.message_b_textview);
                TextView senderLabel = convertView.findViewById(R.id.message_b_sender_name_textview);
                senderLabel.setText(mMessagesList.get(position).getSenderName());
            }
            message.setText(mMessagesList.get(position).getMessage());
        }



//        TextView userNameTextView = convertView.findViewById(R.id.user_name_text_view);
//        userNameTextView.setText(mMessagesList.get(position).getName());
//        userNameTextView.setTextColor(convertView.getResources().getColor(R.color.black));
//        TextView userBalanceTextView = convertView.findViewById(R.id.user_balance_text_view);
//        double balance = mMessagesList.get(position).getBalance();
//        if(balance >= 0){
//            userBalanceTextView.setText(new StringBuilder().append("+").append(String.valueOf(balance)).toString());
//            userBalanceTextView.setTextColor(convertView.getResources().getColor(R.color.green));
//        } else {
//            userBalanceTextView.setText(String.valueOf(balance));
//            userBalanceTextView.setTextColor(convertView.getResources().getColor(R.color.red));
//        }

        return convertView;
    }

}
