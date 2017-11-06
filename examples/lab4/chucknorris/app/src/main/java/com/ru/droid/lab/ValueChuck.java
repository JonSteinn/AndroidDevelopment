package com.ru.droid.lab;

/**
 * Created by Jonni on 11/6/2017.
 */

public class ValueChuck {
    private int id;
    private String joke;
    private String[] categories;
    public ValueChuck(int id, String joke, String[] categories) {
        this.id = id;
        this.joke = joke;
        this.categories = categories;
    }
    public String getJoke() {
        return this.joke;
    }
}
