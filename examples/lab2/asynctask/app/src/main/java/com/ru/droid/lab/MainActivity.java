package com.ru.droid.lab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView message;
    private BackgroundJob task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        message = findViewById(R.id.msg);

        setEventListeners();
    }

    private void setEventListeners() {
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If task does not exists or is not running
                if (task == null || task.getStatus() != AsyncTask.Status.RUNNING) {
                    task = createBackgroundJob();
                    task.execute(5);
                }
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if task does exist and is running
                if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                    task.cancel(true);
                }
            }
        });
    }

    private void taskCleanUp() {
        task = null;
        progressBar.setVisibility(View.INVISIBLE);
    }

    private BackgroundJob createBackgroundJob() {
        return new BackgroundJob(new UiCallback<Integer, Void>() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                message.setText(R.string.running);
            }

            @Override
            public void onProgressUpdate(Integer... values) {
                progressBar.setProgress(values[0]);
            }

            @Override
            public void onPostExecute(Void results) {
                message.setText(R.string.complete);
                taskCleanUp();
            }

            @Override
            public void onCancelled() {
                message.setText(R.string.cancelled);
                taskCleanUp();
            }
        });
    }
}
