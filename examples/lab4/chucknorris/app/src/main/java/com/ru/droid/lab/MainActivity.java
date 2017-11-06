package com.ru.droid.lab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    private static final String CHUCK_URI = "http://api.icndb.com/jokes/random";
    private static final int TIME_OUT_MS = 1000;

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
    }

    public void getChuck(View view) {
        Ion.with(this)
                .load(CHUCK_URI)
                .setTimeout(TIME_OUT_MS)
                .as(new TypeToken<RandomChuck>(){})
                .setCallback(new FutureCallback<RandomChuck>() {
                    @Override
                    public void onCompleted(Exception e, RandomChuck result) {
                        if (e == null) {
                                txt.setText(result.getJoke());
                        } else {
                            txt.setText(R.string.err);
                        }
                    }
        });
    }
}
