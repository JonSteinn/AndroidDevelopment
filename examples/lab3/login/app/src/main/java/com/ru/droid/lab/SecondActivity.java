package com.ru.droid.lab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String un = intent.getStringExtra("USER_NAME");

        String msg = getResources().getString(R.string.welcome) + ", " + un;
        ((TextView)findViewById(R.id.welcome_text)).setText(msg);
    }
}
