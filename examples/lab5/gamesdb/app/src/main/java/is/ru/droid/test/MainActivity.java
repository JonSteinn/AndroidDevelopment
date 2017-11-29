package is.ru.droid.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;

public class MainActivity extends AppCompatActivity {

    private DataAccess data;
    private EditText name, year;
    private List<Game> content;
    private ArrayAdapter<Game> aa;
    private ListView lis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        year = findViewById(R.id.release);
        lis = findViewById(R.id.list);
        data = new DataAccess(this);

        initList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        data.close();
    }

    private void initList() {
        content = data.getAllGames();
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, content);
        lis.setAdapter(aa);
        lis.setOnItemClickListener((parent, view, pos, id) -> {
            editUser(((Game)parent.getItemAtPosition(pos)).getId());
        });
        lis.setOnItemLongClickListener((parent, view, pos, id) -> {
            removeUser(((Game)parent.getItemAtPosition(pos)).getId());
            return true;
        });
    }

    private void editUser(int id) {
        if (!isValid()) {
            Toast.makeText(this, "Fields required", Toast.LENGTH_SHORT).show();
            return;
        }
        Game game = new Game(id, name.getText().toString(), year.getText().toString());
        if (data.updateGame(game)) {
            for (Game g : content) {
                if (g.getId() == id) {
                    g.setName(game.getName());
                    g.setReleaseYear(game.getReleaseYear());
                    break;
                }
            }
            aa.notifyDataSetChanged();
        }
    }

    private void removeUser(int id) {
        if (data.deleteGameByID(String.format(Locale.US, "%d", id))) {
            content.remove(new Game(id, "", ""));
            aa.notifyDataSetChanged();
        }
    }


    private boolean isValid() {
        if (name.getText().toString().trim().length() != 0 &&
                year.getText().toString().trim().length() != 0) {
            return true;
        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.invalid),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void add(View view) {
        if (!isValid()) {
            Toast.makeText(this, "Fields required", Toast.LENGTH_SHORT).show();
            return;
        }
        Game game = new Game(name.getText().toString(), year.getText().toString());
        int id = data.addGame(game);
        if (id < 0) {
            Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show();
        } else {
            game.setId(id);
            content.add(game);
            aa.notifyDataSetChanged();
        }
    }
}