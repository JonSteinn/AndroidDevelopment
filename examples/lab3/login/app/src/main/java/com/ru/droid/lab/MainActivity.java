package com.ru.droid.lab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.username = (EditText)findViewById(R.id.username);
        this.password = (EditText)findViewById(R.id.password);
    }

    public void attemptLogin(View view) {
        String un = username.getText().toString();
        String pw = password.getText().toString();
        if (un.trim().length() == 0) {
            username.requestFocus();
            username.setError(getResources().getString(R.string.required));
        } else if (!FakeDB.doesExist(un)) {
            username.requestFocus();
            username.setError(getResources().getString(R.string.incorrect));
        } else if (pw.trim().length() == 0) {
            password.requestFocus();
            password.setError(getResources().getString(R.string.required));
        } else if (!FakeDB.correctPassword(un, pw)) {
            password.requestFocus();
            password.setError(getResources().getString(R.string.incorrect));
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("USER_NAME", un);
            startActivity(intent);
            finish();
        }
    }
}