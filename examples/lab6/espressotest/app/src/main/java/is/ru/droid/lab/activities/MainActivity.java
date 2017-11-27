package is.ru.droid.lab.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import is.ru.droid.lab.R;

public class MainActivity extends AppCompatActivity {

    private static String path = "https://api.tronalddump.io/random/quote";
    public static void setPath(String tempPath) {
        path = tempPath;
    }

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.api_txt);
    }

    public void doAPIstuff(View view) {
        Ion.with(this)
                .load(path)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            text.setText(result.get("value").getAsString());
                        }
                    }
                });
    }

    public void nextActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
        finish();
    }
}
