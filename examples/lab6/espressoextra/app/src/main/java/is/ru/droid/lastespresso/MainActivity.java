package is.ru.droid.lastespresso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText lhs, rhs;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lhs = findViewById(R.id.lhs);
        rhs = findViewById(R.id.rhs);
        result = findViewById(R.id.answer);
    }

    public void calculate(View view) {
        clear();
        String lhsString = lhs.getText().toString();
        String rhsString = rhs.getText().toString();
        if (valid(lhsString, rhsString)) {
            int lhsInt = Integer.parseInt(lhsString);
            int rhsInt = Integer.parseInt(rhsString);
            int resultInt = lhsInt + rhsInt;
            String resultString = String.format(Locale.US, "%d", resultInt);
            result.setText(resultString);
        }
    }

    private void clear() {
        result.setText(null);
        lhs.setError(null);
        rhs.setError(null);
        lhs.clearFocus();
        rhs.clearFocus();
    }

    private boolean valid(String lhsString, String rhsString) {
        if (lhsString.trim().length() == 0) {
            setError(lhs, getString(R.string.req));
            return false;
        }
        if (rhsString.trim().length() == 0) {
            setError(rhs, getString(R.string.req));
            return false;
        }
        if (!lhsString.matches("\\d+")) {
            setError(lhs, getString(R.string.numb));
            return false;
        }
        if (!rhsString.matches("\\d+")) {
            setError(rhs, getString(R.string.numb));
            return false;
        }
        return true;
    }

    private void setError(EditText inputField, String msg) {
        inputField.requestFocus();
        inputField.setError(msg);
    }
}
