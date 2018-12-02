package io.almp.flatmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.LinkedList;
import java.util.List;

import io.almp.flatmanager.adapter.UsersChceckboxesAdapter;
import io.almp.flatmanager.model.User;


public class PayDebtFragment extends Fragment {
    List<User> usersList;


    public PayDebtFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static PayDebtFragment newInstance(String param1, String param2) {
        return new PayDebtFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_pay_debt, container, false);

        usersList = new LinkedList<>();
        usersList.add(new User(12, 10, "artur", "Artur", "artur@mail.com", 112.6));
        usersList.add(new User(12, 10, "artur", "Artur", "artur@mail.com", 112.6));
        usersList.add(new User(12, 10, "artur", "Artur", "artur@mail.com", 112.6));
        usersList.add(new User(12, 10, "artur", "Artur", "artur@mail.com", 112.6));
        usersList.add(new User(12, 10, "artur", "Artur", "artur@mail.com", 112.6));

        Spinner spinner = rootview.findViewById(R.id.users_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootview.getContext(), R.layout.users_spinner_text_view);
        for(User user: usersList){
            arrayAdapter.add(user.getName());
        }
        spinner.setAdapter(arrayAdapter);


        return rootview;
    }



}
