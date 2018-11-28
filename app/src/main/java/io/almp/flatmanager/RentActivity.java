package io.almp.flatmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.almp.flatmanager.adapter.RentHistoryAdapter;
import io.almp.flatmanager.model.RentHistory;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentActivity extends AppCompatActivity {
    private RentHistoryAdapter adapter;
    private ApiInterface mApiInterface;
    private ImageButton sendRentButton;
    private List<RentHistory> mRentHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        mApiInterface = ApiUtils.getAPIService();
        ArrayList<RentHistory> mRentHistoryList = new ArrayList<>();
        EditText rentValueEditText = findViewById(R.id.rentValueEditText);
        ListView renthistoryListView = findViewById(R.id.rentHistoryListview);
        sendRentButton = findViewById(R.id.sendRentButton);
        sendRentButton.setOnClickListener(view -> {
            float rentValue = Float.valueOf(rentValueEditText.getText().toString());
            RentHistory newRentItem = new RentHistory("dzisiaj", rentValue);
            sendPost(10, rentValue);
            mRentHistoryList.add(newRentItem);
            rentValueEditText.setText("");
            adapter.notifyDataSetChanged();
            sendRentButton.setEnabled(false);
            Toast.makeText(RentActivity.this, "Send", Toast.LENGTH_SHORT).show();
        });
        adapter = new RentHistoryAdapter(this, mRentHistoryList);
        renthistoryListView.setAdapter(adapter);

    }

    public void sendPost(int flat, float value) {
        mApiInterface.rentData(flat, value).enqueue(new Callback<SimpleErrorAnswer>() {
            @Override
            public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Log.e("POST", "Post submitted to API");

                    } else {
                        Toast toast = Toast.makeText(RentActivity.this, "Error", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else {
                    Toast toast = Toast.makeText(RentActivity.this, "Chujwie", Toast.LENGTH_SHORT);
                    toast.show();
                }
                sendRentButton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
                sendRentButton.setEnabled(true);
            }
        });
    }
}
