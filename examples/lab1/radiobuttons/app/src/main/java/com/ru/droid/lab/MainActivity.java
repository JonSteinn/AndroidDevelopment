package com.ru.droid.lab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void radioClick(View view) {
        if (((RadioButton)view).isChecked()) {
            switch (view.getId()) {
                case R.id.r1:
                    Toast.makeText(MainActivity.this, R.string.a, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.r2:
                    Toast.makeText(MainActivity.this, R.string.b, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.r3:
                    Toast.makeText(MainActivity.this, R.string.c, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
