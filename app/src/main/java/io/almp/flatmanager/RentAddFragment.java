package io.almp.flatmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.almp.flatmanager.adapter.RentCallbackInterface;
import io.almp.flatmanager.adapter.UserRentAdapter;
import io.almp.flatmanager.model.User;
import io.almp.flatmanager.model.api.ErrorAnswer;
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

public class RentAddFragment extends Fragment implements RentCallbackInterface {
    private ApiInterface mApiInterface;
    private UserRentAdapter mUserRentAdapter;
    private ListView mRentPerUserListView;
    private EditText mTotalRentEditText;
    private List<User> mUsersList;
    private View mRootView;
    private EditText mExtrasEditText;
    private Button mPostRentButton;

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
        mRootView = rootView;
        // mUserPoints = rootView.findViewById(R.id.all_flatmates_balances);
        int flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
        long uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
        mApiInterface = ApiUtils.getAPIService();

        mExtrasEditText = rootView.findViewById(R.id.extrasEditText);
        mRentPerUserListView = rootView.findViewById(R.id.listview_rent_per_user);
        mTotalRentEditText = rootView.findViewById(R.id.totalRentEditText);
        mPostRentButton = rootView.findViewById(R.id.button_rent_post);
        mPostRentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;
                List<Integer> uids = new ArrayList<>();
                List<Float> values = new ArrayList<>();
                float extrasFloatValue = 0f;
                String extrasStrValue = mExtrasEditText.getText().toString();
                if (!TextUtils.isEmpty(extrasStrValue))
                    extrasFloatValue = Float.parseFloat(extrasStrValue);
                float extrasPerPerson = extrasFloatValue/mUsersList.size();
                float sum = countTotalRentValue();
                for (User u : mUsersList) {
                    uids.add(u.getId());
                    EditText userEditText = mRootView.findViewWithTag(u.getId());
                    String strValue = userEditText.getText().toString();
                    if (TextUtils.isEmpty(strValue)) {
                        Toast.makeText(getContext(), u.getName() + " has empty field.", Toast.LENGTH_LONG).show();
                        valid = false;
                        break;
                    }
                    float floatValue = Float.parseFloat(strValue);
                    values.add(extrasPerPerson + floatValue);
                }

                if (valid) {
                    sendPost(uid, flat_id, (extrasFloatValue+sum), uids, values);
                }
            }
        });

        loadUsers(flat_id);
        mExtrasEditText.setOnFocusChangeListener((view, b) -> updateRents());

        return rootView;
    }


    private void sendPost(long uid, int flat, float value, List<Integer> uids, List<Float> values) {
        mApiInterface.sendRentData(uid, flat, value, uids, values).enqueue(new Callback<SimpleErrorAnswer>() {
            @Override
            public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Log.e("POST", "Post submitted to API");
                        RentMainFragment rentMainFragment = new RentMainFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, rentMainFragment);
                        fragmentTransaction.commit();
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                mPostRentButton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
                mPostRentButton.setEnabled(true);
            }
        });
    }


    private void loadUsers(int flatId) {
        mApiInterface.getUserByFlatId(flatId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    mUsersList = response.body();
                    mUserRentAdapter = new UserRentAdapter(getActivity(), mUsersList, RentAddFragment.this);
                    mRentPerUserListView.setAdapter(mUserRentAdapter);
                } else
                    Toast.makeText(getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    private void updateRents() {
        mTotalRentEditText.setText(String.valueOf(countTotalRentValue()));
    }

    private float countTotalRentValue() {
        float sum = 0f;
        for (User u : mUsersList) {
            EditText userEditText = mRootView.findViewWithTag(u.getId());
            String strValue = userEditText.getText().toString();
            if (TextUtils.isEmpty(strValue))
                continue;
            float floatValue = Float.parseFloat(strValue);
            sum += floatValue;
        }
        String extrasStrValue = mExtrasEditText.getText().toString();
        if (!TextUtils.isEmpty(extrasStrValue)) {
            float extrasFloatValue = Float.parseFloat(extrasStrValue);
            sum += extrasFloatValue;
        }
        return sum;
    }

    @Override
    public void onRentChange() {
        updateRents();
    }
}
