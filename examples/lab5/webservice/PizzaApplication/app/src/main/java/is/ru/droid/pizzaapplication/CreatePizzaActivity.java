package is.ru.droid.pizzaapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

public class CreatePizzaActivity extends AppCompatActivity {

    private TokenAccess ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pizza);

        ta = TokenAccess.getInstance(this);
    }

    public void createPizza(View view) {
        String name = ((EditText)findViewById(R.id.create_name)).getText().toString();
        String description = ((EditText)findViewById(R.id.create_description)).getText().toString();

        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("name", name);
        jsonBody.addProperty("description", description);
        Ion.with(this)
                .load("POST", "http://10.0.2.2:5000/pizza")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("pizza_token", ta.getToken())
                .setJsonObjectBody(jsonBody)
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
