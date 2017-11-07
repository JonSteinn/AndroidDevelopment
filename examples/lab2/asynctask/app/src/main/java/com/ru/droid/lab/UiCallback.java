package com.ru.droid.lab;

/**
 * Created by jonsteinn on 7.11.2017.
 */

public interface UiCallback<Progress, Result> {
    void onPreExecute();
    void onProgressUpdate(Progress... values);
    // you could have specific on error / on success callbacks
    // if postExecute were actually returning something to our UI
    void onPostExecute(Result results);
    void onCancelled();
}