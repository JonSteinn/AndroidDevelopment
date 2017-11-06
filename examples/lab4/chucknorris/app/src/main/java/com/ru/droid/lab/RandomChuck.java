package com.ru.droid.lab;

/**
 * Created by Jonni on 11/6/2017.
 */

public class RandomChuck {
    private String type;
    private ValueChuck value;
    public RandomChuck(String type, ValueChuck value) {
        this.type = type;
        this.value = value;
    }
    public String getJoke() {
        return type.equals("success") ? value.getJoke() : "Error";
    }
}
