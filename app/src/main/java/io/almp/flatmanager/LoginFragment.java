package io.almp.flatmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.almp.flatmanager.model.api.LoginAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import io.almp.flatmanager.service.FirebaseMessagingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Main fragment for class of logging.
 */


public class LoginFragment extends Fragment {
    private Button signUp;
    private Button signIn;
    private TextView login;
    private TextView password;
    private ApiInterface mAPIService;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
    }

    public static LoginFragment newInstance(String param1, String param2) {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        mAPIService = ApiUtils.getAPIService();

        login = rootView.findViewById(R.id.login);
        password = rootView.findViewById(R.id.password);
        signUp = rootView.findViewById(R.id.sign_up);
        signUp.setOnClickListener(v -> {
            String emailS;
            String passwordS;
            emailS = login.getText().toString();
            passwordS = password.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("email", emailS);
            bundle.putString("password", passwordS);

            SignUpFragment signUpFragment = new SignUpFragment();
            signUpFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, signUpFragment);
            onSaveInstanceState(savedInstanceState);
            fragmentTransaction.commit();

        });
        signIn = rootView.findViewById(R.id.sign_in);
        signIn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(login.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                Toast.makeText(getContext(), "Uzupelnij pola", Toast.LENGTH_SHORT).show();
                return;
            }
            signIn.setEnabled(false);
            String fbToken = FirebaseMessagingService.getToken(getContext());
            sendPost(login.getText().toString(), password.getText().toString(), fbToken);
        });
        return rootView;
    }


    public void sendPost(String loginStr, String passwordStr, String fbTokenStr) {
        mAPIService.loginData(loginStr, passwordStr, fbTokenStr).enqueue(new Callback<LoginAnswer>() {
            @Override
            public void onResponse(Call<LoginAnswer> call, Response<LoginAnswer> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Log.e("POST", "Post submitted to API");
                        int flatId = response.body().getFlat();
                        LoginFragment.this.getContext().getSharedPreferences("_", MODE_PRIVATE).edit().putLong("user_id", response.body().getId()).apply();
                        LoginFragment.this.getContext().getSharedPreferences("_", MODE_PRIVATE).edit().putString("user_token", response.body().getToken()).apply();
                        LoginFragment.this.getContext().getSharedPreferences("_", MODE_PRIVATE).edit().putInt("flat_id", flatId).apply();

                        if (flatId == 0) { // no flat
                            Intent intent = new Intent(getContext(), NoFlatActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } else {
                        Toast toast = Toast.makeText(getContext(), getString(R.string.bad_login_or_password), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                    toast.show();
                }
                signIn.setEnabled(true);
            }

            @Override
            public void onFailure(Call<LoginAnswer> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
                signIn.setEnabled(true);
            }
        });
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
