package is.ru.droid.lab;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jonni on 11/27/2017.
 */

public class UserDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MyDatabase mDatabase;
    private UserDao dao;

    private static final String[] names = {
            "Siggi",
            "Gummi",
            "Gunna",
            "Sigga"
    };

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                MyDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = mDatabase.userDao();
        dao.insertUsers(
                new User(names[0]),
                new User(names[1]),
                new User(names[2]),
                new User(names[3])
        );
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getAllTest() {
        List<User> all = dao.getAllUsers();
        assertEquals(4, all.size());
        Set<Integer> allIds = new HashSet<>();
        Set<String> allNames = new HashSet<>();
        for (User u : all) {
            allIds.add(u.id);
            allNames.add(u.name);
        }
        assertEquals(4, allIds.size());
        assertEquals(4, allNames.size());
        assertTrue(allNames.containsAll(Arrays.asList(names)));
    }

    @Test
    public void updateUsersTest() {
        User siggi = getUserWithName("Siggi");
        assertNotEquals(null, siggi);
        User sigga = getUserWithName("Sigga");
        assertNotEquals(null, sigga);
        siggi.name = "Haraldur";
        sigga.name = "Halldora";
        dao.updateUsers(siggi, sigga);
        List<User> all = dao.getAllUsers();
        assertEquals(4, all.size());
        Set<String> names = new HashSet<>();
        for (User u : all) names.add(u.name);
        assertTrue(names.contains("Haraldur"));
        assertTrue(names.contains("Halldora"));
        assertFalse(names.contains("Siggi"));
        assertFalse(names.contains("Sigga"));
    }

    @Test
    public void deleteAllTest() {
        dao.removeAllUsers();
        assertEquals(0, dao.getAllUsers().size());
    }

    @Test
    public void deleteUserTest() {
        User gunna = getUserWithName("Gunna");
        assertNotEquals(null, gunna);
        dao.deleteUsers(gunna);
        List<User> all = dao.getAllUsers();
        Set<String> names = new HashSet<>();
        for (User u : all) names.add(u.name);
        assertEquals(3, all.size());
        assertFalse(names.contains("Gunna"));
    }

    @Test
    public void getByIdTest() {
        User sigga = getUserWithName("Sigga");
        assertNotEquals(null, sigga);
        int siggaId = sigga.id;
        User user = dao.getUserById(siggaId);
        assertNotEquals(null, user);
        assertEquals("Sigga", user.name);
    }

    // Rather cheap but just to get ids for names which
    // are unique in our fake db but don't have to be
    private User getUserWithName(String name) {
        for (User u : dao.getAllUsers()) {
            if (u.name.equals(name)) return u;
        }
        return null;
    }
}
