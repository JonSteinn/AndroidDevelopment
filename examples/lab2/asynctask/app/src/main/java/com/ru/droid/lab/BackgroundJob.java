package com.ru.droid.lab;

import android.os.AsyncTask;

/**
 * Created by jonsteinn on 7.11.2017.
 */

public class BackgroundJob extends AsyncTask<Integer, Integer, Void> {

    private UiCallback<Integer, Void> callback;

    public BackgroundJob(UiCallback<Integer, Void> callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        callback.onPreExecute();
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
    protected void onProgressUpdate(Integer... values) {
        callback.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void results) {
        callback.onPostExecute(results);
        callback = null;
    }

    @Override
    protected void onCancelled() {
        callback.onCancelled();
        callback = null;
    }
}
