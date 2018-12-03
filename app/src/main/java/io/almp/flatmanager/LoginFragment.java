package io.almp.flatmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button signUp;
    private Button signIn;
    private TextView login;
    private TextView password;
    private ApiInterface mAPIService;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
//            Intent mainActivityIntent = new Intent(getContext(), MainActivity.class);
//            startActivity(mainActivityIntent);
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

//                    SaveData(response.body().getId(),response.body().getToken());
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
                        Toast toast = Toast.makeText(getContext(), "Zly login lub haslo", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getContext(), "Chujwie", Toast.LENGTH_SHORT);
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
