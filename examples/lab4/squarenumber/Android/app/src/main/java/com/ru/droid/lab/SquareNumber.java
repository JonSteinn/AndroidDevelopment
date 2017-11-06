package com.ru.droid.lab;

/**
 * Created by Jonni on 11/6/2017.
 */

public class SquareNumber {
    private String status;
    private int number_squared;
    public SquareNumber(String status, int number_squared) {
        this.status = status;
        this.number_squared = number_squared;
    }
    public String getStatus() {
        return this.status;
    }
    public int getNumberSquared() {
        return this.number_squared;
    }
}