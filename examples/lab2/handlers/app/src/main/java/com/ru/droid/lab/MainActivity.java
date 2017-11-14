package com.ru.droid.lab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handler attached to out UI thread's looper
        final Handler handler = new Handler(Looper.getMainLooper());

        final ProgressBar progressBar = findViewById(R.id.progress);
        final Switch aSwitch = findViewById(R.id.thread_switch);

        // On switch listener
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Note that this is a callback so this is true if we went from unchecked to checked
            if (isChecked) {
                // start our new thread to perform long task
                newThread(handler, aSwitch, progressBar).start();
            }
        });
    }

    // Create a new thread to run our long task
    private static Thread newThread(final Handler h, final Switch s, final ProgressBar pb) {
        return new Thread(() -> {
            // Let handler post new runnable to UI thread from background thread
            h.post(() -> {
                s.setClickable(false);
                pb.setVisibility(View.VISIBLE);
            });
            // Wait 5000 ms on backgroundThread (Fake long task)
            SystemClock.sleep(5000);
            // Let handler post new runnable to UI thread from background thread
            h.post(() -> {
                pb.setVisibility(View.INVISIBLE);
                s.setClickable(true);
                s.setChecked(false);
            });
        });
    }
}