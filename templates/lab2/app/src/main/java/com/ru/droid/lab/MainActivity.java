package com.ru.droid.lab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GraphicView canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvas = (GraphicView)findViewById(R.id.canvas);

        // TODO
        // Use another thread to continuously update the circle's position
        // Update the view from the UI thread
        // The ball should bounce on boundaries and must not leave the view
        // You can use canvas.getWidth() and canvas.getHeight() for borders
    }
}