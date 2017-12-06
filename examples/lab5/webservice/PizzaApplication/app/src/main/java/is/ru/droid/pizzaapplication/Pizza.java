package is.ru.droid.pizzaapplication;

import java.util.Locale;

public class Pizza {
    private int id;
    private String name;
    private String description;
    private int likes;

    public Pizza(int id, String name, String description, int likes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pizza pizza = (Pizza) o;

        return id == pizza.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s (%d)", name, likes);
    }
}
