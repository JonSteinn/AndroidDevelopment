package com.ru.droid.lab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupStaticList();
        createDynamicList();
    }

    private void setupStaticList() {
        ListView lis = findViewById(R.id.static_list);
        // Set listener when list items are clicked
        lis.setOnItemClickListener((parent, view, position, id) -> {
            String item = (String)parent.getItemAtPosition(position);
            // Toast is a pop us message that fades out
            Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
        });
    }

    private void createDynamicList() {
        // Create adapter
        String[] items = new String[]{"Item A", "Item B", "Item C"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                MainActivity.this, android.R.layout.simple_list_item_1, items);

        ListView lis = findViewById (R.id.dynamic_list);
        // add adapter to list
        lis.setAdapter(arrayAdapter);
        // Set listener when list items are clicked
        lis.setOnItemClickListener((parent, view, position, id) -> {
            String item = (String)parent.getItemAtPosition(position);
            // Toast is a pop us message that fades out
            Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
        });
    }
}