package io.almp.flatmanager;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import io.almp.flatmanager.adapter.ShoppingHistoryAdapter;
import io.almp.flatmanager.model.ShoppingHistoryEntity;
import io.almp.flatmanager.model.User;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingMainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ApiInterface mApiInterface;
    private ListView mUserBalances;
    private ListView mShoppingHistories;
    private List<User> usersList;
    private List<ShoppingHistoryEntity> shoppingHistoryEntitiesList;
    private Button addShoppingItemButton;
    private Button payDebtButton;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShoppingMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingMainFragment newInstance(String param1, String param2) {
        ShoppingMainFragment fragment = new ShoppingMainFragment();
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


    public void loadUsers(int flatId){
        mApiInterface.getUserByFlatId(flatId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if(response.isSuccessful()){
                    usersList = response.body();
                    BalancesAdapter balancesAdapter = new BalancesAdapter(ShoppingMainFragment.this.getActivity(), usersList);
                    mUserBalances.setAdapter(balancesAdapter);
                } else {
                    Toast toast = Toast.makeText(ShoppingMainFragment.this.getContext(), "Chujwie", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    public void loadShoppingHistory(int flatId){
        mApiInterface.getShoppingHistoryByFlatId(flatId).enqueue(new Callback<List<ShoppingHistoryEntity>>() {
            @Override
            public void onResponse(Call<List<ShoppingHistoryEntity>> call, Response<List<ShoppingHistoryEntity>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if(response.isSuccessful()){
                    shoppingHistoryEntitiesList = response.body();
                    ShoppingHistoryAdapter shoppingAdapter = new ShoppingHistoryAdapter(ShoppingMainFragment.this.getActivity(), shoppingHistoryEntitiesList);
                    mShoppingHistories.setAdapter(shoppingAdapter);
                } else {
                    Toast toast = Toast.makeText(ShoppingMainFragment.this.getContext(), "Chujwie", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<ShoppingHistoryEntity>> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_main, container, false);
        mUserBalances = rootView.findViewById(R.id.all_flatmates_balances);
        mShoppingHistories = rootView.findViewById(R.id.shopping_history_list_view);

        mApiInterface = ApiUtils.getAPIService();
        usersList = new LinkedList<>();
        shoppingHistoryEntitiesList = new LinkedList<>();
        int flatId = 0;
        loadUsers(flatId);
        loadShoppingHistory(flatId);

        addShoppingItemButton = rootView.findViewById(R.id.add_shopping_item_button);
        addShoppingItemButton.setOnClickListener(v->{
            AddShoppingItemFragment addShoppingItemFragment = new AddShoppingItemFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.shopping_fragment_container, addShoppingItemFragment);
            fragmentTransaction.commit();
        });

        payDebtButton = rootView.findViewById(R.id.pay_debt);
        payDebtButton.setOnClickListener(v->{
            PayDebtFragment payDebtFragment = new PayDebtFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.shopping_fragment_container, payDebtFragment);
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
