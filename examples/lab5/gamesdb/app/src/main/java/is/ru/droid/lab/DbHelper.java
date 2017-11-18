package is.ru.droid.lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "GAME.db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)",
                Game.TABLE_NAME, Game.C1, Game.C2, Game.C3, Game.C4);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + Game.TABLE_NAME);
        onCreate(db);
    }

    public boolean addGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Game.C2, game.getName());
        cv.put(Game.C3, game.getDeveloper());
        cv.put(Game.C4, game.getReleaseYear());
        long returnValue = db.insert(Game.TABLE_NAME, null, cv);
        db.close();
        return returnValue != -1L;
    }

    public void updateGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Game.C1, game.getId());
        cv.put(Game.C2, game.getName());
        cv.put(Game.C3, game.getDeveloper());
        cv.put(Game.C4, game.getReleaseYear());
        db.update(Game.TABLE_NAME, cv, Game.C1 + " =  ?",
                new String[]{ String.format(Locale.US, "%d", game.getId()) });
        db.close();
    }

    public void deleteGameByID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Game.TABLE_NAME, Game.C1 + " = ?", new String[]{id});
        db.close();
    }

    public List<Game> getAllGames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Game.TABLE_NAME, null);
        List<Game> games = new ArrayList<>(c.getCount());
        while (c.moveToNext()) {
            games.add(new Game(c.getInt(0), c.getString(1),
                    c.getString(2), c.getString(3)));
        }
        c.close();
        db.close();
        return games;
    }
}
