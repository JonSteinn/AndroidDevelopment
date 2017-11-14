package com.ru.droid.lab;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChooseFragment extends Fragment {
    public ChooseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        setOnClickListeners(getActivity());
    }

    private void setOnClickListeners(final Activity a) {
        for (Integer btnId : FakeDB.getButtons()) {
            a.findViewById(btnId).setOnClickListener(v -> imageClick(v, a));
        }
    }

    private void imageClick(View v, Activity a) {
        Data d = FakeDB.getScreenshot(v.getId());
        if (portraitMode(a)) {
            Intent intent = new Intent(a, ScreenshotActivity.class);
            intent.putExtra("SCREENSHOT_ID", d.getScreenShotId());
            intent.putExtra("SCREENSHOT_TITLE", d.getScreenShotTitle());
            a.startActivity(intent);
        } else {
            ScreenshotFragment ssf = (ScreenshotFragment)getFragmentManager().findFragmentById(R.id.ss_frag_land);
            ssf.setScreenShot(a, d.getScreenShotId(), d.getScreenShotTitle());
        }
    }

    private boolean portraitMode(Activity a) {
        return a.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
