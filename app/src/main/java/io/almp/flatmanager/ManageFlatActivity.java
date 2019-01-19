package io.almp.flatmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.almp.flatmanager.model.User;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main class for managing flat.
 */

public class ManageFlatActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private List<User> usersList;
    private ListView usersListView;

    private ApiInterface mApiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        mApiInterface = ApiUtils.getAPIService();
        usersList = new ArrayList<>();
        final int flat_id = getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);

        usersListView = findViewById(R.id.users_list_view);
        usersListView.setAdapter(adapter);
        usersListView.setOnItemClickListener((adapterView, view, i, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ManageFlatActivity.this);

            builder.setTitle("Confirmation");
            builder.setMessage("Do you want to remove this person from your flat?");

            builder.setPositiveButton("YES", (dialog, which) -> {
                long selectedUserId = usersList.get(i).getId();
                removePersonFromFlat(selectedUserId, flat_id);
                dialog.dismiss();
            });

            builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();
        });
        loadUsers(flat_id);

    }


    private void loadUsers(int flatId) {
        mApiInterface.getUserByFlatId(flatId).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    usersList = response.body();
                    adapter = new ArrayAdapter<>(ManageFlatActivity.this, android.R.layout.simple_list_item_1);
                    List<User> list = response.body();
                    for (User user : list) {
                        adapter.add(Utils.nameParser(user.getName()));
                    }
                    usersListView.setAdapter(adapter);
                } else {
                    Toast toast = Toast.makeText(ManageFlatActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    private void removePersonFromFlat(long uid, int flatId) {
        mApiInterface.removeFromFlat(uid, flatId).enqueue(new Callback<SimpleErrorAnswer>() {
            @Override
            public void onResponse(@NonNull Call<SimpleErrorAnswer> call, @NonNull Response<SimpleErrorAnswer> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    Toast.makeText(ManageFlatActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                    loadUsers(flatId);
                } else
                    Toast.makeText(ManageFlatActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<SimpleErrorAnswer> call, @NonNull Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

}
