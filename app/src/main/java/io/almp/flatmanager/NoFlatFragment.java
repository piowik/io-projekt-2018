package io.almp.flatmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
            Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
        });
        return rootView;
    }
}
