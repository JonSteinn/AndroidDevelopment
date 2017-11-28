package is.ru.droid.testapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDatabase INSTANCE = null;

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    // Allowing main thread queries since this is just for testing
                    // You can see how this can be done off the main thread with RxAndroid here:
                    // https://github.com/googlesamples/android-architecture-components/tree/master/BasicRxJavaSample/#readme
                    INSTANCE = Room
                            .databaseBuilder(context, UserDatabase.class, "user.db")
                            .allowMainThreadQueries()
                            .build();
                    // Add some initial values if empty
                    UserDao dao = INSTANCE.userDao();
                    if (dao.getAllUsers().size() == 0) {
                        dao.addUsers(
                            new User("MASTER_ADMIN", "0", Roles.ADMIN),
                            new User("Luke", "1", Roles.NORMAL),
                            new User("Leia", "2", Roles.NORMAL),
                            new User("Han", "3", Roles.NORMAL)
                        );
                    }
                }
            }
        }
        return INSTANCE;
    }

    public static void destroy() {
        if (INSTANCE != null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE != null) {
                    INSTANCE.close();
                    INSTANCE = null;
                }
            }
        }
    }
}
