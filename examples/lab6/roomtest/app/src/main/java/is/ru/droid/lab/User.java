package is.ru.droid.lab;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "USERS")
class User {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;

    public User(String name) {
        this.name = name;
    }
}