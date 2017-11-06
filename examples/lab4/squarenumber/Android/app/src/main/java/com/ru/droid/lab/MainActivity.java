package com.ru.droid.lab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String URI = "http://10.0.2.2:5000";
    private static final int TIME_OUT_MS = 15000;

    private TextView txt;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
        input = findViewById(R.id.input);
    }

    private boolean valid(int inputLength) {
        return 0 < inputLength && inputLength < 5;
    }

    public void getChuck(final View view) {
        if (!valid(input.getText().toString().trim().length())) {
            txt.setError("Invalid input");
            return;
        }

        // What a nice place for something to animate loading time

        view.setClickable(false);
        Ion.with(this)
                .load(URI + "/" + input.getText())
                .setTimeout(TIME_OUT_MS)
                .as(new TypeToken<SquareNumber>(){})
                .setCallback(new FutureCallback<SquareNumber>() {
                    @Override
                    public void onCompleted(Exception e, SquareNumber result) {
                        if (e == null) {
                            if ("success".equals(result.getStatus())) {
                                txt.setText(String.format(Locale.US, "%d", result.getNumberSquared()));
                            } else {
                                txt.setText(R.string.num_err);
                            }
                        } else {
                            txt.setText(R.string.conn_err);
                        }
                        view.setClickable(true);
                    }
        });
    }
}