package io.almp.flatmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println(uid);
            String flatNameString = flatNameEditText.getText().toString();
            String invitationCode = generateInvitationCode(8);
            mApiInterface.addFlat(flatNameString, invitationCode, uid).enqueue(callback);
        }
    };

    private String generateInvitationCode(int length){
        return RandomStringUtils.random(length, true, true).toUpperCase();
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
    };}
