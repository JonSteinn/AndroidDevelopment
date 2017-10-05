package com.ru.droid.lab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handler attached to out UI thread's looper
        final Handler handler = new Handler(Looper.getMainLooper());

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
        final Switch aSwitch = (Switch)findViewById(R.id.thread_switch);

        // On switch listener
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Note that this is a callback so this is true if we went from unchecked to checked
                if (isChecked) {
                    // Create a new thread to run our long tas
                    Thread backgroundThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Let handler post new runnable to UI thread from background thread
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    aSwitch.setClickable(false);
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            });
                            // Wait 5000 ms on backgroundThread (Fake long task)
                            SystemClock.sleep(5000);
                            // Let handler post new runnable to UI thread from background thread
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    aSwitch.setClickable(true);
                                    aSwitch.setChecked(false);
                                }
                            });
                        }
                    });

                    // start our new thread to perform long task
                    backgroundThread.start();
                }
            }
        });
    }
}