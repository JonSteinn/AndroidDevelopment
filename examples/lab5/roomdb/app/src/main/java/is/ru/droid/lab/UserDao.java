package is.ru.droid.lab;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Jonni on 11/18/2017.
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Query("SELECT * FROM USERS")
    List<User> getAllUsers();

    @Query("SELECT * FROM USERS WHERE USERNAME = :username")
    List<User> getUserByName(String username);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(User user);

    @Query("DELETE FROM USERS WHERE USERNAME = :username")
    void removeUserByName(String username);

    @Query("DELETE FROM USERS")
    void removeAllUsers();
}
