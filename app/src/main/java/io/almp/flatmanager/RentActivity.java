package io.almp.flatmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RentActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        ArrayList<String> rentHistoryList = new ArrayList<>();
        rentHistoryList.add("10/2018 2460zł");
        rentHistoryList.add("11/2018 2510,50zł");
        EditText rentValueEditText = findViewById(R.id.rentValueEditText);
        ListView renthistoryListView = findViewById(R.id.rentHistoryListview);
        ImageButton sendRentButton = findViewById(R.id.sendRentButton);
        sendRentButton.setOnClickListener(view -> {
            // TODO
            rentHistoryList.add(rentValueEditText.getText().toString());
            rentValueEditText.setText("");
            adapter.notifyDataSetChanged();
            Toast.makeText(RentActivity.this, "Send", Toast.LENGTH_SHORT).show();
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rentHistoryList);
        renthistoryListView.setAdapter(adapter);

    }
}
