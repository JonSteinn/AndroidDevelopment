package is.ru.droid.testapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import is.ru.droid.testapp.R;

public class AdminActivity extends AppCompatActivity {

    private static String URI = "http://apis.is/currency/m5";

    private TextView usd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        usd = findViewById(R.id.usd);
    }

    public void getCurrency(View view) {
        Ion.with(this)
                .load(URI)
                .setTimeout(1000)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            double dollars = result
                                    .getAsJsonArray("results")
                                    .get(0)
                                    .getAsJsonObject()
                                    .get("value")
                                    .getAsFloat();
                            usd.setText(getString(R.string.conv_msg, dollars));
                        } else {
                            usd.setText(R.string.err);
                        }
                    }
                });
    }
}
