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
 * Main fragment for rents.
 */

public class RentMainFragment extends Fragment {
    private RentHistoryAdapter mRentHistoryAdapter;
    private ApiInterface mApiInterface;
    private List<RentHistoryItem> mRentHistoryItemList;

    public RentMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rent_main, container, false);
        // mUserPoints = rootView.findViewById(R.id.all_flatmates_balances);

        int flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
        long uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);

        mApiInterface = ApiUtils.getAPIService();
        mRentHistoryItemList = new ArrayList<>();
        ListView rentHistoryListView = rootView.findViewById(R.id.rentHistoryListView);
        Button addRentButton = rootView.findViewById(R.id.button_rent_add);
        addRentButton.setOnClickListener(view -> {
            RentAddFragment rentAddFragment = new RentAddFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, rentAddFragment);
            fragmentTransaction.commit();
        });
        loadRents(flat_id, uid);


        mRentHistoryAdapter = new RentHistoryAdapter(getActivity(), mRentHistoryItemList);
        rentHistoryListView.setAdapter(mRentHistoryAdapter);
        return rootView;
    }


    private void updateRents(List<RentHistoryItem> list) {
        mRentHistoryItemList.clear();
        mRentHistoryItemList.addAll(list);
        mRentHistoryAdapter.notifyDataSetChanged();
    }

    private void loadRents(int flat, long uid) {
        mApiInterface.getRents(flat, uid).enqueue(new Callback<List<RentHistoryItem>>() {
            @Override
            public void onResponse(Call<List<RentHistoryItem>> call, Response<List<RentHistoryItem>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    List<RentHistoryItem> returnedList = response.body();
                    updateRents(returnedList);
                } else {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<RentHistoryItem>> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }
}
