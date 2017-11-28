package is.ru.droid.testapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.List;

import is.ru.droid.testapp.R;
import is.ru.droid.testapp.db.Roles;
import is.ru.droid.testapp.db.User;
import is.ru.droid.testapp.db.UserDao;
import is.ru.droid.testapp.db.UserDatabase;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button signUpBtn;
    private EditText un;
    private EditText pw;
    private UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = UserDatabase.getInstance(this);
        un = findViewById(R.id.un);
        pw = findViewById(R.id.pw);
        loginBtn = findViewById(R.id.login);
        signUpBtn = findViewById(R.id.sign_up);
    }

    public void attemptSignUp(View view) {
        String username = un.getText().toString();
        String password = pw.getText().toString();
        if (!validInput(username, password)) return;
        UserDao dao = db.userDao();
        if (dao.getUserByName(username).size() == 0) {
            Intent intent = new Intent(this, NormalActivity.class);
            intent.putExtra("SESSION", un.getText().toString());
            UserDatabase.destroy();
            startActivity(intent);
            finish();
        } else {
            un.setFocusable(true);
            un.setError(getString(R.string.in_use));
        }

    }

    public void attemptLogin(View view) {
        String req = getString(R.string.required);
        String username = un.getText().toString();
        String password = pw.getText().toString();
        if (!validInput(username, password)) return;

        UserDao dao = db.userDao();
        List<User> userLis = dao.getUserByName(username);
        if (userLis.size() == 0) {
            un.setFocusable(true);
            un.setError(getString(R.string.no_user));
            return;
        }
        User user = userLis.get(0);
        if (!password.equals(user.getPassword())) {
            pw.setFocusable(true);
            pw.setError(getString(R.string.wrong_pass));
        } else if (Roles.ADMIN.equals(user.getRole())) {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
            UserDatabase.destroy();
            finish();
        } else {
            Intent intent = new Intent(this, NormalActivity.class);
            intent.putExtra("SESSION", un.getText().toString());
            UserDatabase.destroy();
            startActivity(intent);
            finish();
        }
    }

    private boolean validInput(String username, String password) {
        String req = getString(R.string.required);
        if (username.trim().length() == 0) {
            un.setError(req);
            un.setFocusable(true);
            return false;
        }
        if (password.trim().length() == 0) {
            pw.setError(req);
            pw.setFocusable(true);
            return false;
        }
        return true;
    }
}
