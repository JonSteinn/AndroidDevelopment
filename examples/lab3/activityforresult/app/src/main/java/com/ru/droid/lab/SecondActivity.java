package com.ru.droid.lab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void sendBackResults(View view) {
        Intent intent = new Intent();
        EditText msg = findViewById(R.id.msg_id);
        intent.putExtra("MSG", msg.getText().toString());
        setResult(RESULT_OK, intent); // Using built in RESULT_OK but custom codes can be made
        finish();
    }
}
