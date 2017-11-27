package is.ru.droid.lab.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import is.ru.droid.lab.R;

public class SecondActivity extends AppCompatActivity {

    private static SharedPreferences INSTANCE = null;
    public static void setInstance(SharedPreferences preferences) {
        INSTANCE = preferences;
    }

    private SharedPreferences preferences;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        preferences = INSTANCE == null ?
                getSharedPreferences("TEST_PREF", MODE_PRIVATE) :
                INSTANCE;
        setTextFromPref();
    }

    public void nextActivity(View view) {
        startActivity(new Intent(this, ThirdActivity.class));
        finish();
    }

    public void addVal(View view) {
        if (preferences.getString("TEST_PREF_KEY", "NULL").equals("NULL")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("TEST_PREF_KEY", "TEST_PREF_VALUE");
            editor.commit(); // ignoring async duties
            setTextFromPref();
        }
    }

    private void setTextFromPref() {
        String value = preferences.getString("TEST_PREF_KEY", "NOT FOUND");
        txt = findViewById(R.id.pref_txt);
        txt.setText(value);
    }
}
