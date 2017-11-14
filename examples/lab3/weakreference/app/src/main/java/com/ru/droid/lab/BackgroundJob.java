package com.ru.droid.lab;

import android.os.AsyncTask;
import android.os.SystemClock;

import java.lang.ref.WeakReference;

public class BackgroundJob extends AsyncTask<Void, Void, Void>{

    private WeakReference<MainActivity> ui;

    public BackgroundJob(WeakReference<MainActivity> activity) {
        this.ui = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i < 10 && !this.isCancelled(); i++) {
            SystemClock.sleep(200);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void results) {
        if (ui.get() != null) ui.get().setText();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        // No need to override, just so we remember this will run when cancelled
    }
}
