package is.ru.droid.lab.activities;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import is.ru.droid.lab.R;
import is.ru.droid.lab.db.MyDatabase;
import is.ru.droid.lab.db.User;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static is.ru.droid.lab.activities.CustomMatches.withListSize;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
public class ThirdActivityTest {

    private final static User[] fakeData = new User[]{
            new User("Palli", "ABC"),
            new User("Palina", "CBA"),
            new User("Sigga", "123"),
            new User("Siggi", "321")
    };

    @Rule
    public final ActivityTestRule<ThirdActivity> mActivity = new ActivityTestRule<ThirdActivity>(
            ThirdActivity.class, true, false) {
                @Override
                protected void beforeActivityLaunched() {
                    Context context = InstrumentationRegistry.getTargetContext();
                    MyDatabase inMemDb = Room.inMemoryDatabaseBuilder(context, MyDatabase.class)
                            .allowMainThreadQueries()
                            .build();
                    inMemDb.userDao().addUsers(fakeData);
                    ThirdActivity.setDb(inMemDb);
                }
            };

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setUp() throws Exception {
        mActivity.launchActivity(new Intent());
        noSleep(mActivity.getActivity());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void listTest() {
        ViewInteraction lst = onView(withId(R.id.my_list));
        lst.check(matches(withListSize(4)));

        for (int i = 0; i < 4; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.my_list))
                    .atPosition(i)
                    .check(matches(withText(containsString(fakeData[i].getUsername()))));
        }
    }

    private void noSleep(final ThirdActivity activity) {
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
