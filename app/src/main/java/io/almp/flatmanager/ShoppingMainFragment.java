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
import io.almp.flatmanager.adapter.ShoppingHistoryAdapter;
import io.almp.flatmanager.model.ShoppingHistoryEntity;
import io.almp.flatmanager.model.User;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShoppingMainFragment extends Fragment {

    private ApiInterface mApiInterface;
    private ListView mUserBalances;
    private ListView mShoppingHistories;
    private List<User> usersList;
    private List<ShoppingHistoryEntity> shoppingHistoryEntitiesList;


    public ShoppingMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void loadUsers(int flatId){
        mApiInterface.getUserByFlatId(flatId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
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
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    public void loadShoppingHistory(int flatId){
        mApiInterface.getShoppingHistoryByFlatId(flatId).enqueue(new Callback<List<ShoppingHistoryEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<ShoppingHistoryEntity>> call, @NonNull Response<List<ShoppingHistoryEntity>> response) {
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
            public void onFailure(@NonNull Call<List<ShoppingHistoryEntity>> call, @NonNull Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        Button addShoppingItemButton = rootView.findViewById(R.id.add_shopping_item_button);
        addShoppingItemButton.setOnClickListener(v->{
            AddShoppingItemFragment addShoppingItemFragment = new AddShoppingItemFragment();
            FragmentTransaction fragmentTransaction;
            if (getFragmentManager() != null) {
                Bundle bundle = new Bundle();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.shopping_fragment_container, addShoppingItemFragment);
                fragmentTransaction.commit();
            }
        });

        Button payDebtButton = rootView.findViewById(R.id.pay_debt);
        payDebtButton.setOnClickListener(v->{
            PayDebtFragment payDebtFragment = new PayDebtFragment();
            FragmentTransaction fragmentTransaction;
            if (getFragmentManager() != null) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.shopping_fragment_container, payDebtFragment);
                fragmentTransaction.commit();
            }
        });


        return rootView;
    }

}
