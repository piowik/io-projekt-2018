package io.almp.flatmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.almp.flatmanager.adapter.MessagesAdapter;
import io.almp.flatmanager.model.Message;
import io.almp.flatmanager.model.api.LoginAnswer;
import io.almp.flatmanager.model.api.MessagesAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private ListView mListView;
    private EditText mNewMessageEditText;
    private ImageButton mSendMessageImagebutton;
    private MessagesAdapter mMessagesAdapter;
    private List<Message> mMessageList = new ArrayList<>();

    private ApiInterface mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAPIService = ApiUtils.getAPIService();
        mListView = findViewById(R.id.messages_listview);
        getMessages(getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L),getSharedPreferences("_", MODE_PRIVATE).getString("user_token", "empty"));


        mListView = findViewById(R.id.messages_listview);
        mNewMessageEditText = findViewById(R.id.new_message_edittext);
        mSendMessageImagebutton = findViewById(R.id.send_message_imagebutton);
        mSendMessageImagebutton.setOnClickListener(view -> {


        });



    }


    private void getMessages(long id, String token){
        mAPIService.getMessages(id,token).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    if(response.body()!=null) {
                        mMessageList = response.body();
                        if (mMessageList.size() > 0) {
                            Collections.sort(mMessageList, new Comparator<Message>() {
                                @Override
                                public int compare(final Message object1, final Message object2) {
                                    return object1.getDate().compareTo(object2.getDate());
                                }
                            });
                        }
                        mMessagesAdapter = new MessagesAdapter(ChatActivity.this,mMessageList);
                        mListView.setAdapter(mMessagesAdapter);

                    }

                }
                else{
                    Toast toast = Toast.makeText(ChatActivity.this, "Chujwie", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast toast = Toast.makeText(ChatActivity.this, "Brak neta", Toast.LENGTH_SHORT);
                toast.show();
            }


        });

    }
}
