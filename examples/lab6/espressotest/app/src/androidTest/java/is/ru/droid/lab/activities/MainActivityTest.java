package is.ru.droid.lab.activities;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import is.ru.droid.lab.R;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MockWebServer server;
    private final String expected = "Why is President Obama allowed to use Air Force One on the campaign trail with Crooked Hillary? She is flying with him tomorrow. Who pays?";

    @Rule
    public final ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(
            MainActivity.class, true, false);

    @Before
    public void setUp() throws Exception {

        this.server = new MockWebServer();
        this.server.start();

        // One of the possible api response copied
        String jsonBody = "{\"appeared_at\":\"2016-07-04T22:30:15\",\"created_at\":\"2016-11-20T01:32:11.749213\",\"quote_id\":\"JC-swZ1MSH67ccmyHb8JVg\",\"tags\":[\"Hillary Clinton\"],\"updated_at\":\"2016-11-20T01:32:11.749213\",\"value\":\"Why is President Obama allowed to use Air Force One on the campaign trail with Crooked Hillary? She is flying with him tomorrow. Who pays?\",\"_links\":{\"self\":{\"href\":\"\\/quote\\/JC-swZ1MSH67ccmyHb8JVg\"}},\"_embedded\":{\"author\":[{\"created_at\":\"2016-11-14T01:14:02.096776\",\"bio\":null,\"author_id\":\"wVE8Y7BoRKCBkxs1JkqAvw\",\"name\":\"Donald Trump\",\"slug\":\"donald-trump\",\"updated_at\":\"2016-11-14T01:14:02.096776\"}],\"source\":[{\"created_at\":\"2016-11-20T01:32:11.340032\",\"filename\":null,\"quote_source_id\":\"5kWBWTAwRZyL_sYkT3Mz0A\",\"remarks\":null,\"updated_at\":\"2016-11-20T01:32:11.340032\",\"url\":\"https:\\/\\/twitter.com\\/realDonaldTrump\\/status\\/750094399456026624\"}]}}";
        this.server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader("cache-control","no-cache, private")
                        .setHeader("content-type", "application/hal+json")
                        .setHeader("date","Mon, 27 Nov 2017 17:15:58 GMT")
                        .setHeader("server", "cloudflare-nginx")
                        .setHeader("via", "1.1 vegur")
                        .setBody(jsonBody));
        MainActivity.setPath(this.server.url("/").toString());

        mActivity.launchActivity(new Intent());
        noSleep(mActivity.getActivity());
    }

    @After
    public void tearDown() {
        try {
            this.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.setPath("https://api.tronalddump.io/random/quote");
    }

    @Test
    public void clickAPIButtonTest() {
        ViewInteraction btn = onView(ViewMatchers.withId(R.id.api_btn));
        ViewInteraction txt = onView(withId(R.id.api_txt));
        btn.perform(click());
        txt.check(matches(withText(expected)));
    }



    private void noSleep(final MainActivity activity) {
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