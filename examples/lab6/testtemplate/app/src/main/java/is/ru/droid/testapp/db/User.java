package is.ru.droid.testapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "USERS")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "USERNAME")
    private final String username;
    @NonNull
    @ColumnInfo(name = "PASSWORD")
    private String password;
    @NonNull
    @ColumnInfo(name = "ROLE")
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;

    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @NonNull
    public String getRole() {
        return role;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public void setRole(@NonNull String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
