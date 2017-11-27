package is.ru.droid.lab;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... users);

    @Query("SELECT * from USERS where id = :id LIMIT 1")
    User getUserById(int id);

    @Update
    void updateUsers(User... users);

    @Delete
    void deleteUsers(User... users);

    @Query("DELETE FROM USERS")
    void removeAllUsers();

    @Query("SELECT * FROM USERS")
    List<User> getAllUsers();
}