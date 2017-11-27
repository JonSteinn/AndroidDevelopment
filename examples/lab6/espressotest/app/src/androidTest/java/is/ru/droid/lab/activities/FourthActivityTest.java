package is.ru.droid.lab.activities;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import is.ru.droid.lab.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FourthActivityTest {

    private static final String key = "message";

    @Rule
    public final ActivityTestRule<FourthActivity> mActivity = new ActivityTestRule<>(
            FourthActivity.class, true, false);

    @Mock
    private FirebaseDatabase mockDB;

    @Mock
    private DatabaseReference mockRef;

    @Mock
    private DataSnapshot dataSnapshot;

    @Before
    public void setUp() throws Exception {

        when(mockDB.getReference(key)).thenReturn(mockRef);
        when(dataSnapshot.getValue(String.class)).thenReturn("init_value");
        Answer<Void> myAnswer = new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                ValueEventListener callback = (ValueEventListener) invocation.getArguments()[0]; // second argument in performAsyncAction
                callback.onDataChange(dataSnapshot); // this would be 1 or 2 in your case
                return null;
            }
        };
        doAnswer(myAnswer).when(mockRef).addValueEventListener(any(ValueEventListener.class));
        when(mockRef.setValue(any(String.class))).thenReturn(null);
        FourthActivity.setDatabase(mockDB);

        mActivity.launchActivity(new Intent());
        noSleep(mActivity.getActivity());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void f() {
        ViewInteraction txt = onView(withId(R.id.display_value));
        txt.check(matches(withText("init_value")));
    }



    private void noSleep(final FourthActivity activity) {
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
