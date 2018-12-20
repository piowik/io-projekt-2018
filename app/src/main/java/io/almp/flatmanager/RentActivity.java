package io.almp.flatmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.almp.flatmanager.adapter.RentHistoryAdapter;
import io.almp.flatmanager.model.RentHistoryItem;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *  Main class for rents.
 */

public class RentActivity extends AppCompatActivity {
    private RentHistoryAdapter mRentHistoryAdapter;
    private ApiInterface mApiInterface;
    private ImageButton sendRentButton;
    private List<RentHistoryItem> mRentHistoryItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        int flat_id = getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
        long uid = getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
        mApiInterface = ApiUtils.getAPIService();
        mRentHistoryItemList = new ArrayList<>();
        EditText rentValueEditText = findViewById(R.id.rentValueEditText);
        ListView rentHistoryListView = findViewById(R.id.rentHistoryListView);
        sendRentButton = findViewById(R.id.sendRentButton);
        loadRents(flat_id);

        sendRentButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(rentValueEditText.getText().toString())) {
                Toast.makeText(this, R.string.rent_value_cannot_be_null, Toast.LENGTH_SHORT).show();
                return;
            }
            float rentValue = Float.valueOf(rentValueEditText.getText().toString());
            sendPost(uid,flat_id, rentValue);
            rentValueEditText.setText("");
            sendRentButton.setEnabled(false);
            loadRents(flat_id);
        });
        mRentHistoryAdapter = new RentHistoryAdapter(this, mRentHistoryItemList);
        rentHistoryListView.setAdapter(mRentHistoryAdapter);
    }

    private void sendPost(long uid, int flat, float value) {
        mApiInterface.sendRentData(uid, flat, value).enqueue(new Callback<SimpleErrorAnswer>() {
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
                    Toast toast = Toast.makeText(RentActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
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

    private void updateRents(List<RentHistoryItem> list) {
        mRentHistoryItemList.clear();
        mRentHistoryItemList.addAll(list);
        mRentHistoryAdapter.notifyDataSetChanged();
    }

    private void loadRents(int flat) {
        mApiInterface.getRents(flat).enqueue(new Callback<List<RentHistoryItem>>() {
            @Override
            public void onResponse(Call<List<RentHistoryItem>> call, Response<List<RentHistoryItem>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    List<RentHistoryItem> returnedList  = response.body();
                    updateRents(returnedList);
                }
                else {
                    Toast toast = Toast.makeText(RentActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                    toast.show();
                }
                sendRentButton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<List<RentHistoryItem>> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
                sendRentButton.setEnabled(true);
            }
        });
    }
}
