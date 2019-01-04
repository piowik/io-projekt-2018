package io.almp.flatmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Fragment for adding rents.
 */

public class RentAddFragment extends Fragment {
    private ApiInterface mApiInterface;
    private ImageButton sendRentButton;

    public RentAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rent_add, container, false);
        // mUserPoints = rootView.findViewById(R.id.all_flatmates_balances);

        int flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
        long uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
        mApiInterface = ApiUtils.getAPIService();
        EditText rentValueEditText = rootView.findViewById(R.id.rentValueEditText);
        sendRentButton = rootView.findViewById(R.id.sendRentButton);
        sendRentButton.setOnClickListener(view -> {
            if (TextUtils.isEmpty(rentValueEditText.getText().toString())) {
                Toast.makeText(getContext(), R.string.rent_value_cannot_be_null, Toast.LENGTH_SHORT).show();
                return;
            }
            float rentValue = Float.valueOf(rentValueEditText.getText().toString());
            sendPost(uid, flat_id, rentValue);
            rentValueEditText.setText("");
            sendRentButton.setEnabled(false);
        });
        return rootView;
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
                        Toast toast = Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
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
