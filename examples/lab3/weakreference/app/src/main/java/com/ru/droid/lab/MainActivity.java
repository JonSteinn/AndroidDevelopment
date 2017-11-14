package com.ru.droid.lab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private BackgroundJob job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (job == null || job.getStatus() != AsyncTask.Status.RUNNING) {
            job = new BackgroundJob(new WeakReference<>(this));
            job.execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        job.cancel(true);
    }

    public void setText() {
        ((TextView)findViewById(R.id.my_text)).setText("DONE");
    }
}
