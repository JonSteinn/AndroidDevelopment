package com.ru.droid.lab;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenshotFragment extends Fragment {
    public ScreenshotFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenshot, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        Activity a = getActivity();
        Intent i = a.getIntent();
        int id = i.getIntExtra("SCREENSHOT_ID", -1);
        String title = i.getStringExtra("SCREENSHOT_TITLE");
        if (id != -1 && title != null) setScreenShot(a, id, title);
    }

    public void setScreenShot(Activity a, int id, String title) {
        ((ImageView)a.findViewById(R.id.ss_img)).setImageResource(id);
        ((TextView)a.findViewById(R.id.ss_title)).setText(title);
    }
}
