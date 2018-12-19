package io.almp.flatmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.almp.flatmanager.R;
import io.almp.flatmanager.model.Message;
import io.almp.flatmanager.model.User;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Class containing methods required to create the proper view for messages.
 */

public class MessagesAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<Message> mMessagesList;
    private LayoutInflater mInflater;

    public MessagesAdapter(Activity activity, List<Message> messagesList) {
        mActivity = activity;
        mMessagesList = messagesList;
    }

    public void setMessagesList(List<Message> messagesList){
        mMessagesList= messagesList;
    }
    public void addMessage(Message message){
        mMessagesList.add(message);
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
        if(mInflater == null)
            mInflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(mMessagesList.get(position).getSenderId()==mActivity.getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L)) {
            convertView = mInflater.inflate(R.layout.message_item_a, parent, false);
            TextView message = convertView.findViewById(R.id.message_a_textview);
            message.setText(String.valueOf(mMessagesList.get(position).getMessage()));
        }else {
            convertView = mInflater.inflate(R.layout.message_item_b, parent, false);
            TextView message = convertView.findViewById(R.id.message_b_textview);
            message.setText(String.valueOf(mMessagesList.get(position).getMessage()));
            TextView senderLabel = convertView.findViewById(R.id.message_b_sender_name_textview);
            senderLabel.setText(mMessagesList.get(position).getSenderName());
        }



        return convertView;
    }

}
