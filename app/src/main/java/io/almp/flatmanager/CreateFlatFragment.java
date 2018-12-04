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

public class CreateFlatFragment extends Fragment {
    private Button saveButton;
    private EditText flatNameEditText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_create_flat, container, false);
        flatNameEditText = rootView.findViewById(R.id.flat_name_edit_text);
        saveButton = rootView.findViewById(R.id.save_flat_name_button);
        saveButton.setOnClickListener(onClickListener);

        return rootView;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                  String flatNameString = flatNameEditText.getText().toString();
        }
    };
}
