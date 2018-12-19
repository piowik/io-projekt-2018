package io.almp.flatmanager;

import android.net.Uri;
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
import io.almp.flatmanager.model.DutiesEntity;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *  Main fragment for class of duties.
 */

public class DutiesMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ApiInterface mApiInterface;
    private ListView mUserPoints;
    private ListView mDutiesTodo;
    private ListView mDutiesHistories;
    private List<DutiesEntity> DutiesEntitiesTodoList;
    private List<DutiesEntity> DutiesEntitiesHistoryList;
    private Button addDutiesItemButton;
    private Button seeStatsButton;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DutiesMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DutiesMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DutiesMainFragment newInstance(String param1, String param2) {
        DutiesMainFragment fragment = new DutiesMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    public void loadDutiesTodo(int flatId){
        mApiInterface.getDutiesTodoByFlatId(flatId).enqueue(new Callback<List<DutiesEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<DutiesEntity>> call, @NonNull Response<List<DutiesEntity>> response) {
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
            public void onFailure(Call<List<DutiesEntity>> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }
/*
    public void loadDutiesHistory(int flatId){
        mApiInterface.getDutiesHistoryByFlatId(flatId).enqueue(new Callback<List<DutiesEntity>>() {
            @Override
            public void onResponse(Call<List<DutiesEntity>> call, Response<List<DutiesEntity>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if(response.isSuccessful()){
                    DutiesEntitiesHistoryList = response.body();
                    DutiesHistoryAdapter DutiesAdapter = new DutiesHistoryAdapter(DutiesMainFragment.this.getActivity(), DutiesEntitiesHistoryList);
                    mDutiesHistories.setAdapter(DutiesAdapter);
                } else {
                    Toast toast = Toast.makeText(DutiesMainFragment.this.getContext(), getString(R.string.something_goes_wrong), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<DutiesEntity>> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duties_main, container, false);
       // mUserPoints = rootView.findViewById(R.id.all_flatmates_balances);
        mDutiesHistories = rootView.findViewById(R.id.duties_history_list_view);
        mDutiesTodo = rootView.findViewById(R.id.duties_todo_list_view);

        mApiInterface = ApiUtils.getAPIService();
        DutiesEntitiesTodoList = new LinkedList<>();
       // DutiesEntitiesHistoryList = new LinkedList<>();
        int flatId = 0;
        loadDutiesTodo(flatId);
       // loadDutiesHistory(flatId);


        addDutiesItemButton = rootView.findViewById(R.id.add_duties_item_button);
        addDutiesItemButton.setOnClickListener(v->{
            AddDutyTodoItemFragment addDutyTodoItemFragment = new AddDutyTodoItemFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.duties_fragment_container, addDutyTodoItemFragment);
            fragmentTransaction.commit();
        });

        seeStatsButton = rootView.findViewById(R.id.see_stats);
        seeStatsButton.setOnClickListener(v->{
            SeeStatsFragment seeStatsFragment = new SeeStatsFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
         //   fragmentTransaction.replace(R.id.duties_fragment_container, seeStatsFragment);
            fragmentTransaction.commit();
        });


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
