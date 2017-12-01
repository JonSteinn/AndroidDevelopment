package is.ru.droid.lastespresso;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    private MainActivity activity;
    private ViewInteraction lhs;
    private ViewInteraction rhs;
    private ViewInteraction btn;
    private ViewInteraction result;

    @Before
    public void setUp() {
        mMainActivityTestRule.launchActivity(new Intent());
        activity = mMainActivityTestRule.getActivity();
        noSleep(activity);
        lhs = onView(withId(R.id.lhs));
        rhs = onView(withId(R.id.rhs));
        btn = onView(withId(R.id.calc));
        result = onView(withId(R.id.answer));
    }

    @After
    public void tearDown() {}

    @Test
    public void noInputTest() {
        btn.perform(click());
        lhs.check(matches(hasFocus()));
        lhs.check(matches(hasErrorText(activity.getString(R.string.req))));
        result.check(matches(withText("")));
    }

    @Test
    public void noLhsTest() {
        rhs.perform(replaceText("55"));
        btn.perform(click());
        lhs.check(matches(hasFocus()));
        lhs.check(matches(hasErrorText(activity.getString(R.string.req))));
        result.check(matches(withText("")));
    }
    @Test
    public void noRhsTest() {
        lhs.perform(replaceText("55"));
        btn.perform(click());
        rhs.check(matches(hasFocus()));
        rhs.check(matches(hasErrorText(activity.getString(R.string.req))));
        result.check(matches(withText("")));
    }
    @Test
    public void bothInvalidTest() {
        lhs.perform(replaceText("12##"));
        rhs.perform(replaceText("abcd"));
        btn.perform(click());
        lhs.check(matches(hasFocus()));
        lhs.check(matches(hasErrorText(activity.getString(R.string.numb))));
        result.check(matches(withText("")));
    }
    @Test
    public void lhsInvalidTest() {
        lhs.perform(replaceText("12##"));
        rhs.perform(replaceText("515"));
        btn.perform(click());
        lhs.check(matches(hasFocus()));
        lhs.check(matches(hasErrorText(activity.getString(R.string.numb))));
        result.check(matches(withText("")));
    }
    @Test
    public void rhsInvalidTest() {
        lhs.perform(replaceText("515"));
        rhs.perform(replaceText("12##"));
        btn.perform(click());
        rhs.check(matches(hasFocus()));
        rhs.check(matches(hasErrorText(activity.getString(R.string.numb))));
        result.check(matches(withText("")));
    }
    @Test
    public void bothValidTest() {
        lhs.perform(replaceText("515"));
        rhs.perform(replaceText("1278"));
        btn.perform(click());
        result.check(matches(withText("1793")));
    }
    private void noSleep(final Activity activity) {
        Runnable wakeUp = () -> activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
        activity.runOnUiThread(wakeUp);
    }

}