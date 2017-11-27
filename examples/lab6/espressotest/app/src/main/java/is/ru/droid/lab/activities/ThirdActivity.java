package is.ru.droid.lab.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import is.ru.droid.lab.R;
import is.ru.droid.lab.db.MyDatabase;
import is.ru.droid.lab.db.User;

public class ThirdActivity extends AppCompatActivity {

    private static MyDatabase INSTANCE = null;
    public static void setDb(MyDatabase newDb) {
        INSTANCE = newDb;
    }

    private ListView list;
    private MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        db = INSTANCE == null ? MyDatabase.getInstance(this) : INSTANCE;
        list = findViewById(R.id.my_list);
        populateList();


    }

    private void populateList() {
        List<User> all = db.userDao().getAllUsers();
        ArrayAdapter<User> userList = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, all);
        list.setAdapter(userList);
    }

    public void nextActivity(View view) {
        startActivity(new Intent(this, FourthActivity.class));
        finish();
    }
}
