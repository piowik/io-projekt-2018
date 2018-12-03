package io.almp.flatmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                    Toast toast = Toast.makeText(PayDebtFragment.this.getContext(), "Chujwie", Toast.LENGTH_SHORT);
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
        usersList = new LinkedList<>();
        spinner = rootview.findViewById(R.id.users_spinner);
        loadUsers(0, rootview); //TODO hardcoded
        money = rootview.findViewById(R.id.debt_edit_text);

        payDebtButton = rootview.findViewById(R.id.save_pay_debt);
        payDebtButton.setOnClickListener(v->{
            String moneyS;
            String selectedUserS;
            moneyS = money.getText().toString();
            selectedUserS = spinner.getSelectedItem().toString();
            User selectedUserU;
            for(User user: usersList){
                if(user.getName().equals(selectedUserS)){
                    selectedUserU = user;
                    System.out.println(selectedUserU.getName());
                }
            }
        });
        //TODO send moneyS and selectedUser to server
        return rootview;
    }



}
