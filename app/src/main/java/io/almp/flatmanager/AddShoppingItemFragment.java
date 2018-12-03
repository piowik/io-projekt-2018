package io.almp.flatmanager;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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


public class AddShoppingItemFragment extends Fragment {
    private List<User> usersList;
    ListView usersCheckboxesListView;
    private ApiInterface mApiInterface;
    UsersCheckboxesAdapter usersCheckboxesAdapter;


    public AddShoppingItemFragment() {
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
                    usersCheckboxesAdapter = new UsersCheckboxesAdapter(AddShoppingItemFragment.this.getActivity(), usersList);
                    usersCheckboxesListView.setAdapter(usersCheckboxesAdapter);
                } else {
                    Toast toast = Toast.makeText(AddShoppingItemFragment.this.getContext(), "Chujwie", Toast.LENGTH_SHORT);
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
        View rootView = inflater.inflate(R.layout.fragment_add_shopping_item, container, false);
        mApiInterface = ApiUtils.getAPIService();
        usersCheckboxesListView = rootView.findViewById(R.id.users_checkboxes_list_view);
        loadUsers(0);

        Button pickDateButton = rootView.findViewById(R.id.pick_date_button);
        DatePicker datePicker = rootView.findViewById(R.id.shopping_date_picker);

        pickDateButton.setOnClickListener(v->{
            int isVisible = datePicker.getVisibility();
            if(isVisible == View.INVISIBLE){
                datePicker.setVisibility(View.VISIBLE);
            } else if(isVisible ==View.VISIBLE){
                datePicker.setVisibility(View.INVISIBLE);
            }
        });

        Button saveButton = rootView.findViewById(R.id.save_item);
        saveButton.setOnClickListener(v->{
            EditText itemNameET = rootView.findViewById(R.id.new_item_name_id);
            EditText itemPriceET = rootView.findViewById(R.id.new_item_price_id);
            String itemNameS = itemNameET.getText().toString();
            String itemPrice = itemPriceET.getText().toString();

            if(itemNameS.equals("")){
                Toast toast = Toast.makeText(AddShoppingItemFragment.this.getContext(), "Item name cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            if(itemPrice.equals("")){
                Toast toast = Toast.makeText(AddShoppingItemFragment.this.getContext(), "Item price cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            System.out.println("Dane ok");

            List<String> usersName = new LinkedList<>();
            List<User> selectedUsers = new LinkedList<>();
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            int numberOfUsers = usersCheckboxesAdapter.getCount();


            for (int i = 0; i < numberOfUsers; i++) {
                User user =(User) usersCheckboxesAdapter.getItem(i);
                CheckBox c = rootView.findViewWithTag(i);
                if(c.isChecked()){
                    usersName.add(c.getText().toString());
                }
            }
            if(usersName.isEmpty()){
                Toast toast = Toast.makeText(AddShoppingItemFragment.this.getContext(), "Choose at least one user", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            for(User user: usersList){
                for(String s: usersName){
                    if(user.getName().equals(s)){
                        selectedUsers.add(user);
                        System.out.println(user.getName());
                    }
                }
            }

            //TODO update balances


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(year).append("-").append(month).append("-").append(day).append(" ");
            String date = stringBuilder.toString();
            long uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
            long userName = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_name", 0L);

            int flat_id = 0; //TODO hardcoded
            mApiInterface.addShoppingItem(flat_id, uid, itemNameS, itemPrice, date).enqueue(new Callback<SimpleErrorAnswer>() {
                @Override
                public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {
                }

                @Override
                public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
                    Toast toast = Toast.makeText(AddShoppingItemFragment.this.getContext(), "Dodawanie zakupu nie udało się", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

        });



        return rootView;
    }


}
