package is.ru.droid.pizzaapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class LikePizzaActivity extends AppCompatActivity {

    private TokenAccess ta;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_pizza);

        ta = TokenAccess.getInstance(this);

        Intent i = getIntent();

        String title = i.getStringExtra("NAME");
        String description = i.getStringExtra("DESCRIPTION");
        id = i.getIntExtra("ID", - 1);

        ((TextView)findViewById(R.id.like_title)).setText(title);
        ((TextView)findViewById(R.id.like_description)).setText(description);
    }

    public void likePizza(View view) {
        Ion.with(this)
                .load("GET", "http://10.0.2.2:5000/pizza/" + id)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("pizza_token", ta.getToken())
                .asJsonObject()
                .setCallback((e, result) -> {
                    if (e != null) {
                        toast(e.getMessage());
                    } else if (!"success".equals(result.get("msg").getAsString())) {
                        toast(result.get("msg").getAsString());
                    } else {
                        goBack();
                    }
                });
    }

    private void goBack() {
        finish();
    }
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
