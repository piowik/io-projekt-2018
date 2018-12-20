package io.almp.flatmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.almp.flatmanager.model.api.JoinFlatAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Main fragment for class of joining flats.
 */


public class JoinFlatFragment extends Fragment {
    private ApiInterface mApiInterface;
    private Button mJoinFlatButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_join_flat, container, false);
        EditText codeTextView = rootView.findViewById(R.id.flat_code_edit_text);
        mApiInterface = ApiUtils.getAPIService();
        mJoinFlatButton = rootView.findViewById(R.id.button_join_flat);
        mJoinFlatButton.setOnClickListener(view -> {
            String flatCode = codeTextView.getText().toString();
            if (!TextUtils.isEmpty(flatCode)) {
                mJoinFlatButton.setEnabled(false);
                long uid = getContext().getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
                joinFlat(uid, flatCode);
            }
        });
        return rootView;
    }

    public void joinFlat(long uid, String code) {
        mApiInterface.joinFlat(uid, code).enqueue(new Callback<JoinFlatAnswer>() {
            @Override
            public void onResponse(Call<JoinFlatAnswer> call, Response<JoinFlatAnswer> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Log.e("FlatId", response.body().getFlatId() + "");
                        getContext().getSharedPreferences("_", MODE_PRIVATE).edit().putInt("flat_id", response.body().getFlatId()).apply();

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                mJoinFlatButton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<JoinFlatAnswer> call, Throwable t) {
                Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                Log.e("POST", "Unable to submit post to API.");
                mJoinFlatButton.setEnabled(true);
            }
        });
    }
}
