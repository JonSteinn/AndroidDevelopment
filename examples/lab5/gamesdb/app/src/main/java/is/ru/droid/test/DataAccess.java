package is.ru.droid.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public int addGame(Game game) {
        ContentValues cv = new ContentValues();
        cv.put(Game.C2, game.getName());
        cv.put(Game.C3, game.getReleaseYear());
        return (int)db.insert(Game.TABLE_NAME, null, cv);

    }

    public boolean updateGame(Game game) {
        ContentValues cv = new ContentValues();
        cv.put(Game.C1, game.getId());
        cv.put(Game.C2, game.getName());
        cv.put(Game.C3, game.getReleaseYear());
        return db.update(Game.TABLE_NAME, cv, Game.C1 + " =  ?",
                new String[]{ String.format(Locale.US, "%d", game.getId()) }) > 0;
    }

    public boolean deleteGameByID(String id) {
        return db.delete(Game.TABLE_NAME, Game.C1 + " = ?", new String[]{id}) > 0;
    }

    public List<Game> getAllGames() {
        Cursor c = db.rawQuery("SELECT * FROM " + Game.TABLE_NAME, null);
        List<Game> games = new ArrayList<>(c.getCount());
        while (c.moveToNext()) {
            games.add(new Game(c.getInt(0), c.getString(1),
                    c.getString(2)));
        }
        c.close();
        return games;
    }
}