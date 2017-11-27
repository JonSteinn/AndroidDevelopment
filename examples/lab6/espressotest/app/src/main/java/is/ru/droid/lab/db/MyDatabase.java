package is.ru.droid.lab.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();


    public static MyDatabase getInstance(Context context) {
        MyDatabase db = Room.databaseBuilder(context, MyDatabase.class, "MY_ROOM_DB")
                .allowMainThreadQueries() // DON'T ALLOW THIS!
                .build();
        // For simplification, we add some users if none exists so we have some when we run our app
        if (db.userDao().getAllUsers().size() == 0) {
            db.userDao().addUsers(
                    new User("Gummi", "123123"),
                    new User("Siggi", "556677"),
                    new User("Magga", "810551")
            );
        }
        return db;
    }
}