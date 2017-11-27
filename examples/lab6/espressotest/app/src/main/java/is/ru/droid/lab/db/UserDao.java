package is.ru.droid.lab.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUsers(User... user);

    @Query("SELECT * FROM USERS")
    List<User> getAllUsers();

    @Query("DELETE FROM USERS WHERE USERNAME = :username")
    void removeUserByName(String username);

    @Query("DELETE FROM USERS")
    void removeAllUsers();
}