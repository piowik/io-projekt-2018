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


public class AddDutyItemFragment extends Fragment {
    private List<User> usersList;
  //  ListView usersCheckboxesListView;
    private ApiInterface mApiInterface;
    long uid;
    int flat_id;


    public AddDutyItemFragment() {
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
                } else {
                    Toast toast = Toast.makeText(AddDutyItemFragment.this.getContext(), "Chujwie", Toast.LENGTH_SHORT);
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
        View rootView = inflater.inflate(R.layout.fragment_add_duty_item, container, false);
        mApiInterface = ApiUtils.getAPIService();
   //     usersCheckboxesListView = rootView.findViewById(R.id.users_checkboxes_list_view);
   //     uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
    //    flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
   //     loadUsers(flat_id);

        Button saveButton = rootView.findViewById(R.id.save_duty);
        saveButton.setOnClickListener(v->{
            EditText dutyNameET = rootView.findViewById(R.id.new_duty_name_id);
            EditText dutyValueET = rootView.findViewById(R.id.new_duty_value_id);
            String dutyName = dutyNameET.getText().toString();
            String dutyValue = dutyValueET.getText().toString();

            if(dutyName.equals("")){
                Toast toast = Toast.makeText(AddDutyItemFragment.this.getContext(), "Duty name cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            if(dutyValue.equals("")){
                Toast toast = Toast.makeText(AddDutyItemFragment.this.getContext(), "Duty value cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            System.out.println("Dane ok");


            mApiInterface.addDutyItem(flat_id, uid, dutyName, dutyValue).enqueue(callback);
            DutiesMainFragment dutyMainFragment = new DutiesMainFragment();

            FragmentTransaction fragmentTransaction;
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.duties_fragment_container, dutyMainFragment);
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
