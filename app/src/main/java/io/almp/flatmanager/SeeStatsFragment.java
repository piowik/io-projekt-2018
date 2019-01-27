package io.almp.flatmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import io.almp.flatmanager.adapter.BalancesAdapter;
import io.almp.flatmanager.adapter.PointsAdapter;
import io.almp.flatmanager.model.ShoppingHistoryEntity;
import io.almp.flatmanager.model.User;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * TODO
 *  Main fragment for class of statistics for each user.
 */


public class SeeStatsFragment extends Fragment {

    private ApiInterface mApiInterface;
    private ListView mUserPoints;
    private List<User> usersList;
    private int flat_id;


    public SeeStatsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void loadUsers(int flatId) {
        mApiInterface.getUserByFlatIdPoints(flatId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    usersList = response.body();
                    PointsAdapter pointsAdapter = new PointsAdapter(SeeStatsFragment.this.getActivity(), usersList);
                    mUserPoints.setAdapter(pointsAdapter);
                } else {
                    Toast toast = Toast.makeText(SeeStatsFragment.this.getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_see_stats, container, false);
            mUserPoints = rootView.findViewById(R.id.all_flatmates_points);
            flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
            mApiInterface = ApiUtils.getAPIService();
            usersList = new LinkedList<>();
            loadUsers(flat_id);

            return rootView;
        }


    private final Callback<SimpleErrorAnswer> callback = new Callback<SimpleErrorAnswer>() {
        @Override
        public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {
            System.out.println("i guess its ok");

        }

        @Override
        public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
            System.out.println("i guess its not ok");

        }
    };
}
