package com.ru.droid.lab;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateLayout();
    }

    private void populateLayout() {
        LinearLayout root = findViewById(R.id.root_view);
        for (Map.Entry<Integer, TeamInfo> info : FakeDB.getAllImages()) {
            addNewTeam(root, info.getKey(), info.getValue());
        }
    }

    private void addNewTeam(LinearLayout parent, final int img, final TeamInfo info) {

        // Create new view with inflate
        View team = View.inflate(this, R.layout.team, null);

        // Set button image source and on click listener
        ImageButton btn = team.findViewById(R.id.team_img);
        btn.setImageResource(img);
        btn.setOnClickListener(v -> Toast.makeText(MainActivity.this, info.getName(), Toast.LENGTH_SHORT).show());

        Resources res = getResources();

        // Set club's name using resource string format
        TextView name = team.findViewById(R.id.team_title);
        name.setText(res.getString(R.string.team_name, info.getName()));

        // Set club's epl title count using resource string format
        TextView epl = team.findViewById(R.id.team_epl);
        epl.setText(res.getString(R.string.team_epl, info.getEplTitles()));

        // Set club's cl title count using resource string format
        TextView cl = team.findViewById(R.id.team_cl);
        cl.setText(res.getString(R.string.team_cl, info.getClTitles()));

        // Add the view to our root view group
        parent.addView(team);

    }
}
