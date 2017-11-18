package is.ru.droid.lab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyDatabase db;
    private EditText un;
    private EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = MyDatabase.getInstance(this);
        testInitDB();
        un = findViewById(R.id.un);
        pw = findViewById(R.id.pw);
    }

    public void attemptLogin(View view) {
        if (validateInput(false)) {
            toast("LOGIN ::: HI!!!");
        }
    }

    public void attemptSignUp(View view) {
        if (validateInput(true)) {
            toast("SIGNUP ::: WELCOME !!!");
            db.userDao().addUser(new User(un.getText().toString(), pw.getText().toString()));
            toastCount();
        }
    }

    public void attemptRemove(View view) {
        if (validateInput(false)) {
            toast("REMOVE ::: BYE!!!");
            db.userDao().removeUserByName(un.getText().toString());
            toastCount();
        }
    }

    private void testInitDB() {
        db.userDao().removeAllUsers();
        if (db.userDao().getAllUsers().size() != 0) {
            toast("FAILED TO REMOVE ALL!");
        } else {
            db.userDao().addUser(new User("Gummi", "1234"));
            db.userDao().addUser(new User("Siggi", "abcd"));
            db.userDao().addUser(new User("Saga", "ab12"));
            toastCount();
        }

    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean validateInput(boolean signUp) {
        un.setError(null);
        pw.setError(null);
        if (isEmpty(un)) return false;
        if (isEmpty(pw)) return false;
        String user = un.getText().toString();
        if (signUp) {
            if (!db.userDao().getUserByName(user).isEmpty()) {
                un.setFocusable(true);
                un.setError("Already in use");
                return false;
            }
        } else {
            List<User> users = db.userDao().getUserByName(user);
            if (users.isEmpty()) {
                un.setFocusable(true);
                un.setError("User does not exist");
                return false;
            }
            if (!pw.getText().toString().equals(users.get(0).password)) {
                pw.setFocusable(true);
                pw.setError("Invalid password");
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() == 0) {
            editText.setFocusable(true);
            editText.setError("Required");
            return true;
        }
        return false;
    }

    private void toastCount() {
        toast(db.userDao().getAllUsers().size() + " in db!!!!");
    }
}
