package is.ru.droid.lab;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Jonni on 11/18/2017.
 */

@Entity(tableName = "USERS")
public class User {

    @PrimaryKey
    @NonNull
    public final String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
