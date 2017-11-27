package is.ru.droid.lab.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.WindowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import is.ru.droid.lab.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecondActivityTest {

    @Rule
    public final ActivityTestRule<SecondActivity> mActivity = new ActivityTestRule<>(
            SecondActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        SharedPreferences preferences = mock(SharedPreferences.class);
        when(preferences.getString("TEST_PREF_KEY", "NOT FOUND")).thenReturn("TEST_PREF_VALUE");
        SecondActivity.setInstance(preferences);

        mActivity.launchActivity(new Intent());
        noSleep(mActivity.getActivity());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void preferenceTextTest() {
        ViewInteraction txt = onView(ViewMatchers.withId(R.id.pref_txt));
        txt.check(matches(withText("TEST_PREF_VALUE")));
    }



    private void noSleep(final SecondActivity activity) {
        Runnable wakeUpDevice = new Runnable() {
            @Override
            public void run() {
                activity.getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                );
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }
}
