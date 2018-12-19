package io.almp.flatmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.content.Context.MODE_PRIVATE;

/**
 *  Main fragment for class of flat not found.
 */


public class NoFlatFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_no_flat, container, false);
        Button joinFlatButton = rootView.findViewById(R.id.button_join_flat);
        Button createFlatButton = rootView.findViewById(R.id.button_create_flat);
        Button logoutButton = rootView.findViewById(R.id.button_logout);

        joinFlatButton.setOnClickListener(view -> {
            JoinFlatFragment joinFlatFragment = new JoinFlatFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, joinFlatFragment);
            onSaveInstanceState(savedInstanceState);
            fragmentTransaction.commit();
        });
        createFlatButton.setOnClickListener(view -> {
            CreateFlatFragment createFlatFragment = new CreateFlatFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, createFlatFragment);
            onSaveInstanceState(savedInstanceState);
            fragmentTransaction.commit();
        });
        logoutButton.setOnClickListener(view -> {
            SharedPreferences sharedPref = getContext().getSharedPreferences("_", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("user_id", 0L);
            editor.putInt("flat_id", -1);
            editor.putString("user_token", "empty");
            editor.apply();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        return rootView;
    }
}
