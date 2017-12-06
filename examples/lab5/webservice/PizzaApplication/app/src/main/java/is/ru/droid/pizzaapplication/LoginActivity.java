package is.ru.droid.pizzaapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class LoginActivity extends AppCompatActivity {

    private TokenAccess ta;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_un);
        password = findViewById(R.id.login_pw);
        ta = TokenAccess.getInstance(this);
        if (ta.hasToken()) proceed();
    }

    public void attemptSignUp(View view) {
        String un = username.getText().toString();
        String pw = password.getText().toString();
        createUser(un, pw);
    }

    private void createUser(String un, String pw) {
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("name", un);
        jsonBody.addProperty("password", pw);
        Ion.with(this)
                .load("POST", "http://10.0.2.2:5000/user")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .setJsonObjectBody(jsonBody)
                .asJsonObject()
                .setCallback((e, result) -> {
                    if (e != null) {
                        toast(e.getMessage());
                    } else if (!"success".equals(result.get("msg").getAsString())) {
                        toast(result.get("msg").getAsString());
                    } else {
                        getToken(un, pw);
                    }
                });
    }

    public void attemptSignIn(View view) {
        String un = username.getText().toString();
        String pw = password.getText().toString();
        getToken(un, pw);
    }

    private void getToken(String un, String pw) {
        String credentials = new String(Base64.encode(String.format("%s:%s", un, pw).getBytes(), Base64.DEFAULT));
        Ion.with(this)
                .load("GET", "http://10.0.2.2:5000/login")
                .addHeader("Authorization", String.format("Basic %s", credentials))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .asJsonObject()
                .setCallback((e, result) -> {
                    if (e != null) {
                        toast(e.getMessage());
                    } else if (result.get("msg") != null) {
                        toast(result.get("msg").getAsString());
                    } else {
                        ta.setToken(result.get("token").getAsString());
                        proceed();
                    }
                });
    }



    private void proceed() {
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
