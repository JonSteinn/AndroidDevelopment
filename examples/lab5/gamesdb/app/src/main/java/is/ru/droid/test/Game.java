package is.ru.droid.test;

import java.util.Locale;

public class Game {

    public static final String TABLE_NAME = "GAME";
    public static final String C1 = "ID";
    public static final String C2 = "NAME";
    public static final String C3 = "RELEASE_YEAR";

    private int id;
    private String name;
    private String releaseYear;

    public Game(String name, String releaseYear) {
        this(-1, name, releaseYear);
    }

    public Game(int id, String name, String releaseYear) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{%s: %d, %s: %s, %s: %s}",
                C1, id, C2, name, C3, releaseYear);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}