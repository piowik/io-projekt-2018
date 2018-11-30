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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.almp.flatmanager.adapter.MessagesAdapter;
import io.almp.flatmanager.model.Message;
import io.almp.flatmanager.model.api.LoginAnswer;
import io.almp.flatmanager.model.api.MessagesAnswer;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
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
        mMessagesAdapter = new MessagesAdapter(this,mMessageList);
        mListView.setAdapter(mMessagesAdapter);
        scrollListView();
        mNewMessageEditText = findViewById(R.id.new_message_edittext);
        mSendMessageImagebutton = findViewById(R.id.send_message_imagebutton);
        mSendMessageImagebutton.setOnClickListener(view -> {
            if(mNewMessageEditText.getText().toString().trim().length() > 0){
                addMessage(getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L),getSharedPreferences("_", MODE_PRIVATE).getString("user_token", "empty"),
                        mNewMessageEditText.getText().toString());
            }else {
                Toast toast = Toast.makeText(ChatActivity.this, "Nie można wysłać pustej wiadomości", Toast.LENGTH_SHORT);
                toast.show();
            }

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
                            Collections.sort(mMessageList, (object1, object2) -> object1.getDate().compareTo(object2.getDate()));
                        }
                        mMessagesAdapter.setMessagesList(mMessageList);
                        mMessagesAdapter.notifyDataSetChanged();
                        scrollListView();
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

    private void addMessage(Long id, String token, String message){

        mAPIService.addMessage(id,token,message).enqueue(new Callback<SimpleErrorAnswer>() {
            @Override
            public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {

                if (response.isSuccessful()&&response.body()!=null&&!response.body().isError()) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    mMessagesAdapter.addMessage(new Message(id,dateFormat.format(date),message,getString(R.string.me)));
                    mMessagesAdapter.notifyDataSetChanged();
                    mNewMessageEditText.getText().clear();
                    scrollListView();
                }
                else{
                    Toast toast = Toast.makeText(ChatActivity.this, "Kurza twarz, coś poszło nie tak", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
                Toast toast = Toast.makeText(ChatActivity.this, "Wysyłanie wiadomości nie powiodło się", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private void scrollListView(){
        mListView.setSelection(mMessagesAdapter.getCount() - 1);
    }
}
