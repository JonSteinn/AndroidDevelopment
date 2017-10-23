package com.ru.droid.lab.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ru.droid.lab.R;
import com.ru.droid.lab.activities.ScreenshotActivity;
import com.ru.droid.lab.db.Data;
import com.ru.droid.lab.db.FakeDB;

public class ChooseFragment extends Fragment {


    public ChooseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        setOnClickListeners(getActivity());
    }

    private void setOnClickListeners(final Activity a) {
        a.findViewById(R.id.btn_carma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClick(v, a);
            }
        });
        a.findViewById(R.id.btn_dung).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClick(v, a);
            }
        });
        a.findViewById(R.id.btn_mdk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClick(v, a);
            }
        });
        a.findViewById(R.id.btn_jazz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClick(v, a);
            }
        });
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
        return a.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

}
