package is.ru.droid.lab;

import java.util.Locale;

public class Game {

    public static final String TABLE_NAME = "GAME";
    public static final String C1 = "ID";
    public static final String C2 = "NAME";
    public static final String C3 = "DEVELOPER";
    public static final String C4 = "RELEASE_YEAR";

    private int id;
    private String name;
    private String developer;
    private String releaseYear;

    public Game(String name, String developer, String releaseYear) {
        this(-1, name, developer, releaseYear);
    }

    public Game(int id, String name, String developer, String releaseYear) {
        this.id = id;
        this.name = name;
        this.developer = developer;
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "{%s: %d, %s: %s, %s: %s, %s: %s}",
                C1, id, C2, name, C3, developer, C4, releaseYear);
    }
}
