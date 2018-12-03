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
    long uid;
    int flat_id;


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
        uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
        flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
        loadUsers(flat_id);

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
            int numbOfSelectedUsers = selectedUsers.size();
            double costPerUser = Double.valueOf(itemPrice) / numbOfSelectedUsers;
            costPerUser = costPerUser * (-1);
            double costForBuyer = Double.valueOf(itemPrice) + costPerUser;





            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(year).append("-").append(month).append("-").append(day).append(" ");
            String date = stringBuilder.toString();

            for (User user: selectedUsers){
                if(user.getId() != uid){
                    mApiInterface.updateUserBalance(user.getId(), costPerUser).enqueue(callback);
                    System.out.println(user.getName() + ". " + user.getId());
                } else if(user.getId() == uid){
                    mApiInterface.updateUserBalance(uid, costForBuyer).enqueue(callback);
                }
            }

            mApiInterface.addShoppingItem(flat_id, uid, itemNameS, itemPrice, date).enqueue(callback);
            ShoppingMainFragment shoppingMainFragment = new ShoppingMainFragment();

            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.shopping_fragment_container, shoppingMainFragment);
            fragmentTransaction.commit();

        });



        return rootView;
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
