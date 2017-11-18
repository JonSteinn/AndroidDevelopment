package is.ru.droid.lab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DbHelper db;
    private EditText name, dev, year;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        dev = findViewById(R.id.developer);
        year = findViewById(R.id.release);

        table = findViewById(R.id.table);

        db = new DbHelper(this);

        populateTable();
    }

    public void add(View view) {
        if (!isValid()) {
            return;
        }
        db.addGame(new Game(
                name.getText().toString(),
                dev.getText().toString(),
                year.getText().toString()
        ));
        populateTable();
    }

    private void populateTable() {
        table.removeAllViews();
        List<Game> games = db.getAllGames();
        for (Game g : games) {
            TableRow[] tableRows = getRows(g);
            table.addView(tableRows[0]);
            table.addView(tableRows[1]);
        }
    }

    private boolean isValid() {
        if (name.getText().toString().trim().length() != 0 &&
                dev.getText().toString().trim().length() != 0 &&
                year.getText().toString().trim().length() != 0) {
            return true;
        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.invalid),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private TableRow[] getRows(Game g){

        TableRow upper = new TableRow(this);
        upper.addView(getTextViewWithText(g.getName()));
        upper.addView(getTextViewWithText(g.getDeveloper()));
        upper.addView(getTextViewWithText(g.getReleaseYear()));

        TableRow lower = new TableRow(this);
        Button btn1 = new Button(this);
        btn1.setText(R.string.edit);
        btn1.setOnClickListener((view) -> {
            if (isValid()) {
                Game updated = new Game(
                        g.getId(),
                        name.getText().toString(),
                        dev.getText().toString(),
                        year.getText().toString()
                );
                db.updateGame(updated);
                populateTable();
            }
        });
        lower.addView(btn1);

        Button btn2 = new Button(this);
        btn2.setText(R.string.remove);
        btn2.setOnClickListener((view) -> {
            db.deleteGameByID(String.format(Locale.US, "%d", g.getId()));
            populateTable();
        });
        lower.addView(btn2);

        return new TableRow[]{upper, lower};
    }

    private TextView getTextViewWithText(String text) {
        TextView tv1 = new TextView(this);
        tv1.setText(text);
        return tv1;
    }
}
