package com.ru.droid.lab;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private static final String COLOR_PREFERENCE = "COLOR";
    private SeekBar r, b, g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVariables();
        setListeners();
        setStartValues();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveBeforeLeaving();
    }

    private void setVariables() {
        r = findViewById(R.id.r_bar);
        g = findViewById(R.id.g_bar);
        b = findViewById(R.id.b_bar);
    }

    private void setListeners() {
        SeekBar.OnSeekBarChangeListener listener =  new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        r.setOnSeekBarChangeListener(listener);
        g.setOnSeekBarChangeListener(listener);
        b.setOnSeekBarChangeListener(listener);
    }

    void setStartValues() {
        SharedPreferences color = getSharedPreferences(COLOR_PREFERENCE, MODE_PRIVATE);
        int red = color.getInt("r", -1);
        int green = color.getInt("g", -1);
        int blue = color.getInt("b", -1);
        if (red == -1 || green == -1 || blue == -1) {
            red = 255;
            green = 255;
            blue = 255;
        }
        r.setProgress(red);
        g.setProgress(green);
        b.setProgress(blue);
    }

    private void setColor() {
        findViewById(R.id.root).setBackgroundColor(
                Color.rgb(r.getProgress(), g.getProgress(), b.getProgress()));
    }

    private void saveBeforeLeaving() {
        SharedPreferences.Editor editor = getSharedPreferences(COLOR_PREFERENCE, MODE_PRIVATE).edit();
        editor.putInt("r", r.getProgress());
        editor.putInt("g", g.getProgress());
        editor.putInt("b", b.getProgress());
        editor.apply();
    }
}
