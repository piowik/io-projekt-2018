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

import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ApiInterface mApiInterface;
    private ImageButton sendRentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        mApiInterface = ApiUtils.getAPIService();
        ArrayList<String> rentHistoryList = new ArrayList<>();
        rentHistoryList.add("10/2018 2460zł");
        rentHistoryList.add("11/2018 2510,50zł");
        EditText rentValueEditText = findViewById(R.id.rentValueEditText);
        ListView renthistoryListView = findViewById(R.id.rentHistoryListview);
        sendRentButton = findViewById(R.id.sendRentButton);
        sendRentButton.setOnClickListener(view -> {
            // TODO
            rentHistoryList.add(rentValueEditText.getText().toString());
            sendPost(10, Float.valueOf(rentValueEditText.getText().toString()));

            rentValueEditText.setText("");
            adapter.notifyDataSetChanged();
            sendRentButton.setEnabled(false);
            Toast.makeText(RentActivity.this, "Send", Toast.LENGTH_SHORT).show();
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rentHistoryList);
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
//                    SaveData(response.body().getId(),response.body().getToken());
                        Intent intent = new Intent(RentActivity.this, RentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
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
