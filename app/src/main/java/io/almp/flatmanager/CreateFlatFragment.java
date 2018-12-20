package io.almp.flatmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.commons.lang3.RandomStringUtils;


import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Class containing methods required to create properly functioning 'create flat' tab.
 */

public class CreateFlatFragment extends Fragment {
    private Button saveButton;
    private EditText flatNameEditText;
    private ApiInterface mApiInterface;
    private long uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_create_flat, container, false);
        mApiInterface = ApiUtils.getAPIService();
        uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
        flatNameEditText = rootView.findViewById(R.id.flat_name_edit_text);
        saveButton = rootView.findViewById(R.id.save_flat_name_button);
        saveButton.setOnClickListener(onClickListener);

        return rootView;
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println(uid);
            String flatNameString = flatNameEditText.getText().toString();
            String invitationCode = generateInvitationCode();
            mApiInterface.addFlat(flatNameString, invitationCode, uid).enqueue(callback);
        }
    };

    private String generateInvitationCode(){
        return RandomStringUtils.random(6, true, true).toUpperCase();
    }

    private final Callback<SimpleErrorAnswer> callback = new Callback<SimpleErrorAnswer>() {
        @Override
        public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {
            Toast.makeText(getContext(), R.string.success, Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPref = getContext().getSharedPreferences("_", MODE_PRIVATE);
            io.almp.flatmanager.service.FirebaseMessagingService.sendPost(sharedPref.getLong("user_id", 0L),"empty");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("user_id", 0L);
            editor.putInt("flat_id", -1);
            editor.putString("user_token", "empty");
            editor.apply();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        @Override
        public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
            System.out.println("i guess its not ok");
        }
    };}
