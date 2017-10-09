package com.ru.droid.lab;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progress;
    private Button button;

    // RxAndroid: Observer and observable
    private Observer<String> displayData;
    private Observable<String> fetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setObserverAndObservable();

        textView = (TextView)findViewById(R.id.text);
        progress = (ProgressBar)findViewById(R.id.progress);
        button = (Button)findViewById(R.id.button);

        // Subscribe on click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setClickable(false);
                fetchData.subscribe(displayData);
            }
        });
    }

    private void setObserverAndObservable() {
        fetchData = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                SystemClock.sleep(2500);
                return getString(R.string.download_success);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        // Handles updating UI given observable
        displayData = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                progress.setVisibility(View.VISIBLE);
                textView.setText(R.string.download_task);
            }

            @Override
            public void onNext(@NonNull String s) {
                textView.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(MainActivity.this, R.string.download_fail, Toast.LENGTH_SHORT).show();
                textView.setText(R.string.error);
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, R.string.download_success, Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
                button.setClickable(true);
            }
        };
    }
}