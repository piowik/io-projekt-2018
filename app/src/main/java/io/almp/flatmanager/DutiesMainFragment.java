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

import io.almp.flatmanager.adapter.DutiesTodoAdapter;
import io.almp.flatmanager.adapter.DutiesHistoryAdapter;
import io.almp.flatmanager.model.DutiesHistoryEntity;
import io.almp.flatmanager.model.DutiesTodoEntity;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Main fragment for class of duties.
 */

public class DutiesMainFragment extends Fragment {
    private ApiInterface mApiInterface;
    private ListView mDutiesTodo;
    private ListView mDutiesHistories;
    private List<DutiesTodoEntity> DutiesEntitiesTodoList;
    private List<DutiesHistoryEntity> DutiesEntitiesHistoryList;
    private Button addDutiesItemButton;
    private Button seeStatsButton;
    private int flat_id;

    public DutiesMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void loadDutiesTodo(int flatId){
        mApiInterface.getDutiesTodoByFlatId(flatId).enqueue(new Callback<List<DutiesTodoEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<DutiesTodoEntity>> call, @NonNull Response<List<DutiesTodoEntity>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if(response.isSuccessful()){
                    DutiesEntitiesTodoList = response.body();
                    DutiesTodoAdapter dutiesTodoAdapter = new DutiesTodoAdapter(DutiesMainFragment.this.getActivity(), DutiesEntitiesTodoList);
                    mDutiesTodo.setAdapter(dutiesTodoAdapter);
                } else {
                    Toast toast = Toast.makeText(DutiesMainFragment.this.getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<DutiesTodoEntity>> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    public void loadDutiesHistory(int flatId){
        mApiInterface.getDutiesHistoryByFlatId(flatId).enqueue(new Callback<List<DutiesHistoryEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<DutiesHistoryEntity>> call, @NonNull Response<List<DutiesHistoryEntity>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if(response.isSuccessful()){
                    DutiesEntitiesHistoryList = response.body();
                    DutiesHistoryAdapter dutiesHistoryAdapter = new DutiesHistoryAdapter(DutiesMainFragment.this.getActivity(), DutiesEntitiesHistoryList);
                    mDutiesHistories.setAdapter(dutiesHistoryAdapter);
                } else {
                    Toast toast = Toast.makeText(DutiesMainFragment.this.getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<DutiesHistoryEntity>> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duties_main, container, false);
        mDutiesHistories = rootView.findViewById(R.id.duties_history_list_view);
        mDutiesTodo = rootView.findViewById(R.id.duties_todo_list_view);
        flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
        mApiInterface = ApiUtils.getAPIService();
        DutiesEntitiesTodoList = new LinkedList<>();
        DutiesEntitiesHistoryList = new LinkedList<>();
        loadDutiesTodo(flat_id);
        loadDutiesHistory(flat_id);


        addDutiesItemButton = rootView.findViewById(R.id.add_duties_item_button);
        addDutiesItemButton.setOnClickListener(v->{
            AddDutyTodoItemFragment addDutyTodoItemFragment = new AddDutyTodoItemFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.duties_fragment_container, addDutyTodoItemFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        seeStatsButton = rootView.findViewById(R.id.see_stats);
        seeStatsButton.setOnClickListener(v->{
            SeeStatsFragment seeStatsFragment = new SeeStatsFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.duties_fragment_container, seeStatsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });


        return rootView;
    }
}
