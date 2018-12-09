package io.almp.flatmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.almp.flatmanager.model.api.ErrorAnswer;
import io.almp.flatmanager.model.api.LoginAnswer;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    private EditText mLogin;
    private EditText mPassword;
    private EditText mPasswordConfirmation;
    private EditText mEmail;
    private EditText mName;
    private OnFragmentInteractionListener mListener;
    private Button mSignUp;
    private ApiInterface mAPIService;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAPIService = ApiUtils.getAPIService();


        mLogin = rootView.findViewById(R.id.sing_up_login_edittext);
        mPassword = rootView.findViewById(R.id.sing_up_password_edittext);
        mSignUp = rootView.findViewById(R.id.sign_up_button);
        mSignUp.setOnClickListener(v->{
            if(validData()){
                sendPost(mLogin.getText().toString(),mPassword.getText().toString(),mName.getText().toString(),mEmail.getText().toString());
            }
        });



        return rootView;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private boolean validData(){
        if(!(mLogin.getText().toString().trim().length() > 0)){
            showToast(getString(R.string.empty_login));
            return false;
        }
        if(mPassword.getText().toString().trim().length() > 0 && mPasswordConfirmation.getText().toString().trim().length() > 0){
            if(!mPassword.getText().toString().equals(mPasswordConfirmation.getText().toString()))
                showToast(getString(R.string.different_passwords));
            else{
                if(mPassword.getText().toString().length()<8)
                    showToast(getString(R.string.too_short_password));
            }

        }else{
            showToast(getString(R.string.empty_password));
            return false;
        }
        if(!(mEmail.getText().toString().trim().length() > 0)){
            showToast(getString(R.string.empty_email));
            return false;
        }
        if(!(mName.getText().toString().trim().length() > 0)){
            showToast(getString(R.string.empty_name));
            return false;
        }
        return true;

    }
    private void showToast(String message){
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
    private void sendPost(String loginStr, String passwordStr, String nameStr, String emailStr) {
        mAPIService.singup(loginStr, passwordStr, nameStr,emailStr).enqueue(new Callback<ErrorAnswer>() {
            @Override
            public void onResponse(Call<ErrorAnswer> call, Response<ErrorAnswer> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        showToast(getString(R.string.singup_correct));
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        if(response.body().getMessage().equals("Login in use"))
                            showToast(getString(R.string.login_in_use));
                        else
                            showToast(getString(R.string.singup_faild));

                    }

                }
            }

            @Override
            public void onFailure(Call<ErrorAnswer> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
                showToast(getString(R.string.something_goes_wrong));
            }
        });
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
