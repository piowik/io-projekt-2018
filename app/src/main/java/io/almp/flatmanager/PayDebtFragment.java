package io.almp.flatmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import io.almp.flatmanager.adapter.UsersCheckboxesAdapter;
import io.almp.flatmanager.model.User;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Main fragment for class of paying debt.
 */


public class PayDebtFragment extends Fragment {
    List<User> usersList;
    ArrayAdapter<String> arrayAdapter;
    private ApiInterface mApiInterface;
    Spinner spinner;
    Button payDebtButton;
    EditText money;


    public PayDebtFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static PayDebtFragment newInstance(String param1, String param2) {
        return new PayDebtFragment();
    }


    public void loadUsers(int flatId, View rootview){
        mApiInterface.getUserByFlatId(flatId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if(response.isSuccessful()){
                    usersList = response.body();
                    arrayAdapter = new ArrayAdapter<>(rootview.getContext(), R.layout.users_spinner_text_view);
                    List<User> list = response.body();
                    for(User user: list){
                        arrayAdapter.add(user.getName());
                    }
                    spinner.setAdapter(arrayAdapter);
                } else {
                    Toast toast = Toast.makeText(PayDebtFragment.this.getContext(), getString(R.string.something_goes_wrong), Toast.LENGTH_SHORT);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_pay_debt, container, false);
        mApiInterface = ApiUtils.getAPIService();
        int flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
        long uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
        usersList = new LinkedList<>();
        spinner = rootview.findViewById(R.id.users_spinner);
        loadUsers(flat_id, rootview);
        money = rootview.findViewById(R.id.debt_edit_text);



        payDebtButton = rootview.findViewById(R.id.save_pay_debt);
        payDebtButton.setOnClickListener(v->{
            String moneyS;
            String selectedUserS;
            moneyS = money.getText().toString();
            double cost = Double.valueOf(moneyS);
            if(moneyS.equals("")){
                Toast toast = Toast.makeText(PayDebtFragment.this.getContext(), "Enter value", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if(spinner.getSelectedItem()==null){
                Toast toast = Toast.makeText(PayDebtFragment.this.getContext(), "Choose one user", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            selectedUserS = spinner.getSelectedItem().toString();
            User selectedUserU;
            for(User user: usersList){
                if(user.getName().equals(selectedUserS)){
                    selectedUserU = user;
                    System.out.println(selectedUserU.getName());
                    mApiInterface.updateUserBalance(selectedUserU.getId(), -cost).enqueue(callback);
                    mApiInterface.updateUserBalance(uid, cost).enqueue(callback);
                }
            }
            ShoppingMainFragment shoppingMainFragment = new ShoppingMainFragment();
            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.shopping_fragment_container, shoppingMainFragment);
            fragmentTransaction.commit();


        });
        //TODO send moneyS and selectedUser to server
        return rootview;
    }
    private Callback<SimpleErrorAnswer> callback = new Callback<SimpleErrorAnswer>() {
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
