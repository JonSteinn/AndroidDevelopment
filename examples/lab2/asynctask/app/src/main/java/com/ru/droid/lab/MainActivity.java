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
    private MyTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progress);
        message = (TextView)findViewById(R.id.msg);

        setEventListeners();
    }

    private void setEventListeners() {
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If task does not exists or is not running
                if (task == null || task.getStatus() != AsyncTask.Status.RUNNING) {
                    task = new MyTask();
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

    // Params and Progress are Integer while Result are Void.
    public class MyTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            message.setText(R.string.running);
        }

        @Override
        protected Void doInBackground(Integer... params) {
            long startTime = System.currentTimeMillis();
            long endTime = params[0] * 1000 + startTime;
            long timeInterval = endTime - startTime;
            long timeNow;
            int last = 0;
            // Run for params[0] seconds given that the task has not been canceled.
            while (!this.isCancelled() && (timeNow = System.currentTimeMillis()) < endTime) {
                // Calculate ratio
                int progress = (int)(1000 * (timeNow - startTime) / (double)timeInterval);
                // Send progress update if it has changed (no need otherwise)
                if (progress != last) {
                    last = progress;
                    publishProgress(progress);
                }
            }
            return null; // return null with Void result
        }

        @Override
        protected void onPostExecute(Void results) {
            message.setText(R.string.complete);
            taskCleanUp();
        }

        @Override
        protected void onCancelled() {
            message.setText(R.string.cancelled);
            taskCleanUp();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }

    private void taskCleanUp() {
        task = null;
        progressBar.setVisibility(View.INVISIBLE);
    }
}
