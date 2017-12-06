package is.ru.droid.pizzaapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private TokenAccess ta;
    private ListView lis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ta = TokenAccess.getInstance(this);
        lis = findViewById(R.id.pizza_list);
        lis.setOnItemClickListener((parent, view, pos, id) -> {
            Pizza curr = (Pizza)parent.getItemAtPosition(pos);
            Intent intent = new Intent(this, LikePizzaActivity.class);
            intent.putExtra("NAME", curr.getName());
            intent.putExtra("DESCRIPTION", curr.getDescription());
            intent.putExtra("ID", curr.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateList();
    }

    private void populateList() {
        Ion.with(this)
                .load("GET", "http://10.0.2.2:5000/pizza")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("pizza_token", ta.getToken())
                .asJsonObject()
                .setCallback((e, result) -> {
                    if (e != null) {
                        toast(e.getMessage());
                    } else {
                        JsonArray jArr = result.getAsJsonArray("pizzas");
                        ArrayList<Pizza> aLis = new ArrayList<>();
                        for (int i = 0; i < jArr.size(); i++) {
                            JsonObject pizza = jArr.get(i).getAsJsonObject();
                            aLis.add(new Pizza(
                                pizza.get("id").getAsInt(),
                                pizza.get("name").getAsString(),
                                pizza.get("description").getAsString(),
                                pizza.get("likes").getAsInt()
                            ));
                        }
                        ArrayAdapter<Pizza> aa = new ArrayAdapter<>(
                                this, android.R.layout.simple_list_item_1, aLis);
                        lis.setAdapter(aa);
                    }
                });
    }

    public void removeToken(View view) {
        ta.clearToken();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void createPizza(View view) {
        startActivity(new Intent(this, CreatePizzaActivity.class));
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
