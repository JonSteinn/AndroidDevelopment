package com.ru.droid.lab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText first, second;
    private TextView answer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: set fields
    }

    public void calculate(View view) {
        try {
            if (inputIsEmpty()) {
                answer.setText(R.string.err_msg);
            } else {
                // Convert text fields to integers for arithmetic
                int lhs = Integer.parseInt(first.getText().toString());
                int rhs = Integer.parseInt(second.getText().toString());
                // TODO: calculations based on button pressed
                // TODO: set answer display
            }
        }catch (NumberFormatException ex) {
            answer.setText(R.string.err_msg);
        }
    }

    private boolean inputIsEmpty() {
        return first.getText().toString().trim().length() == 0 ||
                second.getText().toString().trim().length() == 0;
    }
}