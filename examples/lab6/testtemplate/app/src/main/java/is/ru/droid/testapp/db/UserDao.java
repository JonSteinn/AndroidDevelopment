package is.ru.droid.testapp.db;

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

    @Query("SELECT * FROM USERS WHERE USERNAME = :username")
    List<User> getUserByName(String username);
}
