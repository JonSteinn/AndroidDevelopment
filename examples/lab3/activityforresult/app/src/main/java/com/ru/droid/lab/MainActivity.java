package com.ru.droid.lab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, REQ_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ID) {
            if (resultCode == RESULT_OK) {
                String val = data.getStringExtra( "MSG");
                ((Button) findViewById(R.id.btn)).setText(val == null ? "" : val);
            } else {
                Toast.makeText(this, R.string.err1, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.err2, Toast.LENGTH_SHORT).show();
        }
    }
}