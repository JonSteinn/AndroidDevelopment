package is.ru.droid.testapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import is.ru.droid.testapp.R;
import is.ru.droid.testapp.util.BinaryConverter;

public class NormalActivity extends AppCompatActivity {

    private char[] str;
    private int index;

    private TextView binary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        Intent intent = getIntent();
        String name = intent.getStringExtra("SESSION");
        if (name != null) {
            TextView tv = findViewById(R.id.greeting);
            tv.setText(getString(R.string.welcome_messages, name));
        }

        binary = findViewById(R.id.binary);
        str = new char[8];
        resetString();
    }

    public void addNext(View view) {
        if (index == -1) resetString();
        switch(view.getId()) {
            case R.id.btn0:
                str[index--] = '0';
                break;
            case R.id.btn1:
                str[index--] = '1';
                break;
        }
        if (index == -1) {
            String s = new String(str);
            binary.setText(String.format(Locale.US, "%s = %d", s, BinaryConverter.toInt(s)));
        } else {
            binary.setText(new String(str));
        }
    }

    private void resetString() {
        for (int i = 0; i < 8; i++) {
            str[i] = ' ';
        }
        index = 7;
    }
}
