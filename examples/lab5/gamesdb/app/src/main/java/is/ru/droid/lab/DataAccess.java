package is.ru.droid.lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jonni on 11/19/2017.
 */

public class DataAccess {

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DataAccess(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    public boolean addGame(Game game) {
        ContentValues cv = new ContentValues();
        cv.put(Game.C2, game.getName());
        cv.put(Game.C3, game.getDeveloper());
        cv.put(Game.C4, game.getReleaseYear());
        long returnValue = db.insert(Game.TABLE_NAME, null, cv);
        return returnValue != -1L;
    }

    public void updateGame(Game game) {
        ContentValues cv = new ContentValues();
        cv.put(Game.C1, game.getId());
        cv.put(Game.C2, game.getName());
        cv.put(Game.C3, game.getDeveloper());
        cv.put(Game.C4, game.getReleaseYear());
        db.update(Game.TABLE_NAME, cv, Game.C1 + " =  ?",
                new String[]{ String.format(Locale.US, "%d", game.getId()) });
    }

    public void deleteGameByID(String id) {
        db.delete(Game.TABLE_NAME, Game.C1 + " = ?", new String[]{id});
    }

    public List<Game> getAllGames() {
        Cursor c = db.rawQuery("SELECT * FROM " + Game.TABLE_NAME, null);
        List<Game> games = new ArrayList<>(c.getCount());
        while (c.moveToNext()) {
            games.add(new Game(c.getInt(0), c.getString(1),
                    c.getString(2), c.getString(3)));
        }
        c.close();
        return games;
    }
}
