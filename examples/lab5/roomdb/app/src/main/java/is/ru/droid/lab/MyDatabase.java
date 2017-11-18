package is.ru.droid.lab;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public static MyDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, MyDatabase.class, "MY_ROOM_DB")
                .allowMainThreadQueries() // DON'T ALLOW THIS!
                .build();
    }
}
