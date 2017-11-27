package is.ru.droid.lab.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import is.ru.droid.lab.R;

public class FourthActivity extends AppCompatActivity {

    private static FirebaseDatabase DB_INSTANCE = null;
    public static void setDatabase(FirebaseDatabase newDb) {
        DB_INSTANCE = newDb;
    }

    private TextView txt;
    private EditText in;

    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        txt = findViewById(R.id.display_value);
        in = findViewById(R.id.enter_value);

        db = DB_INSTANCE == null ? FirebaseDatabase.getInstance() : DB_INSTANCE;
        DatabaseReference myRef = db.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                txt.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void postData(View view) {
        String input = in.getText().toString();
        if (input.trim().length() == 0) {
            return;
        }
        DatabaseReference myRef = db.getReference("message");
        myRef.setValue(input);
    }
}
