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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import io.almp.flatmanager.R;
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
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    DatePicker datePicker;
    Button pickDateButton;
    EditText itemNameET;
    EditText itemPriceET;



    public AddShoppingItemFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    usersCheckboxesAdapter = new UsersCheckboxesAdapter(AddShoppingItemFragment.this.getActivity(), usersList);
                    usersCheckboxesListView.setAdapter(usersCheckboxesAdapter);
                    for(User user: usersList){
                        arrayAdapter.add(user.getName());
                    }
                    spinner.setAdapter(arrayAdapter);
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

        loadUsers(flat_id, rootView);
        spinner = rootView.findViewById(R.id.users_spinner_buyer);


        pickDateButton = rootView.findViewById(R.id.pick_date_button);
        datePicker = rootView.findViewById(R.id.shopping_date_picker);

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
            saveShoppingButtonListener(rootView);
        });



        return rootView;
    }

    private static class emptyArgumentException extends Exception{
        String message;
        private emptyArgumentException(String message) {
            this.message = message;
        }
    }

    Callback<SimpleErrorAnswer> callback = new Callback<SimpleErrorAnswer>() {
        @Override
        public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {
            System.out.println("i guess its ok");

        }

        @Override
        public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
            System.out.println("i guess its not ok");

        }
    };

    String getItemName(View rootView) throws emptyArgumentException{
        itemNameET = rootView.findViewById(R.id.new_item_name_id);
        String itemName = itemNameET.getText().toString();
        if(itemName.equals("")){
            throw new emptyArgumentException(getString(R.string.empty_item_name));
        }
        return itemName;
    }

    double getItemPrice(View rootView) throws emptyArgumentException {
        itemPriceET = rootView.findViewById(R.id.new_item_price_id);
        String itemPrice = itemPriceET.getText().toString();
        if(itemPrice.equals("")){
            throw new emptyArgumentException(getString(R.string.empty_price));
        }
        return Double.valueOf(itemPrice);
    }

    String getPurchaseDate(){
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        return String.valueOf(year) + "-" + month + "-" + day + " ";
    }

    long getBuyerId() {
        String selectedBuyer;
        selectedBuyer = spinner.getSelectedItem().toString();
        User selectedBuyerU;
        long buyerId = -1;
        for(User user: usersList){
            if(user.getName().equals(selectedBuyer)){
                selectedBuyerU = user;
                buyerId = selectedBuyerU.getId();
            }
        }
        return buyerId;
    }

    List<Integer> getPayersIds(View rootView) throws emptyArgumentException{
        int numberOfUsers = usersCheckboxesAdapter.getCount();
        List<String> usersName = new LinkedList<>();
        List<Integer> selectedUsers = new LinkedList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            CheckBox c = rootView.findViewWithTag(i);
            if(c.isChecked()){
                usersName.add(c.getText().toString());
            }
        }

        for(User user: usersList){
            for(String s: usersName){
                if(user.getName().equals(s)){
                    selectedUsers.add(user.getId());
                    System.out.println(user.getName());
                }
            }
        }
        if(selectedUsers.isEmpty()){
            throw new emptyArgumentException("Choose al least one payer");
        }
        return selectedUsers;
    }

    void saveShoppingButtonListener(View rootView){
        String itemNameS;
        double itemPrice;
        String date;
        long buyerId;
        List<Integer> payersIds;

        try {
            itemNameS = getItemName(rootView);
            itemPrice = getItemPrice(rootView);
            payersIds = getPayersIds(rootView);
        } catch (emptyArgumentException e) {
            Toast toast = Toast.makeText(AddShoppingItemFragment.this.getContext(), e.message, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        date = getPurchaseDate();
        buyerId = getBuyerId();




        mApiInterface.updateUserBalance(buyerId, itemPrice).enqueue(callback);

        int numbOfSelectedUsers = payersIds.size();
        double costPerUser = itemPrice / numbOfSelectedUsers;
        costPerUser = costPerUser * (-1);
        double costForBuyer = itemPrice + costPerUser;



        for (Integer payerId: payersIds){
            System.out.println(payerId);
            System.out.println(buyerId);
            if(payerId != buyerId){
                mApiInterface.updateUserBalance(payerId, costPerUser).enqueue(callback);
            } else {
                mApiInterface.updateUserBalance(buyerId, costForBuyer).enqueue(callback);
            }
        }

        mApiInterface.addShoppingItem(flat_id, buyerId, itemNameS, itemPrice, date).enqueue(callback);
        ShoppingMainFragment shoppingMainFragment = new ShoppingMainFragment();

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        fragmentTransaction.replace(R.id.shopping_fragment_container, shoppingMainFragment);
        fragmentTransaction.commit();

    }


}
