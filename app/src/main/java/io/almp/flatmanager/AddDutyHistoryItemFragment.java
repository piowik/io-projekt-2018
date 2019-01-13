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
import android.widget.Toast;

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
 *  Class containing methods required to create properly functioning adding duties to do method.
 */

public class AddDutyHistoryItemFragment extends Fragment {
    private ApiInterface mApiInterface;
    private long user_id;
    private int flat_id;
    private ArrayAdapter<String> arrayAdapter;

    public AddDutyHistoryItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_add_duty_item, container, false);
//        mApiInterface = ApiUtils.getAPIService();
//        flat_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
//        user_id = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
//        Button saveButton = rootView.findViewById(R.id.save_duty);
//        saveButton.setOnClickListener(v->{
//            EditText dutyValueET = rootView.findViewById(R.id.new_duty_value_id);
//            EditText dutyNameET = rootView.findViewById(R.id.new_duty_name_id);
//            String dutyValue = dutyValueET.getText().toString();
//            String dutyName = dutyNameET.getText().toString();
//
//            if(dutyValue.equals("")){
//                Toast toast = Toast.makeText(AddDutyTodoItemFragment.this.getContext(), "Duty value cannot be empty", Toast.LENGTH_SHORT);
//                toast.show();
//                return;
//            }
//
//            if(dutyName.equals("")){
//                Toast toast = Toast.makeText(AddDutyTodoItemFragment.this.getContext(), "Duty name cannot be empty", Toast.LENGTH_SHORT);
//                toast.show();
//                return;
//            }
//
//            System.out.println("Dane ok");
//
//            mApiInterface.addDutyTodo(flat_id, user_id, dutyValue, dutyName).enqueue(callback);
//            DutiesMainFragment dutyMainFragment = new DutiesMainFragment();
//
//            FragmentTransaction fragmentTransaction;
//            fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.duties_fragment_container, dutyMainFragment);
//            fragmentTransaction.commit();
//        });
//
//        return rootView;
//    }
//
//    private final Callback<SimpleErrorAnswer> callback = new Callback<SimpleErrorAnswer>() {
//        @Override
//        public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {
//            System.out.println("i guess its ok");
//
//        }
//
//        @Override
//        public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
//            System.out.println("i guess its not ok");
//
//        }
//    };
//

}
