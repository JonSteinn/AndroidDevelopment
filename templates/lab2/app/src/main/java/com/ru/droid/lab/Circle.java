package com.ru.droid.lab;

/**
 * Created by Jonni on 10/4/2017.
 */

public class Circle {
    private float x;
    private float y;
    private float radius;
    private float horizontalSpeed;
    private float verticalSpeed;

    private static Circle main;
    public static Circle getCircle() {
        if (main == null) main = new Circle();
        return main;
    }

    public Circle() {
        x = 150;
        y = 250;
        radius = 50;
        horizontalSpeed = 0.01f;
        verticalSpeed = 0.01f;
    }

    public void update() {
        x += horizontalSpeed;
        y += verticalSpeed;
    }

    public void changeHorizontalDirection() {
        horizontalSpeed = -horizontalSpeed;
    }

    public void changeVerticalDirection() {
        verticalSpeed = -verticalSpeed;
    }

    public boolean positiveHorizontalDirection() {
        return horizontalSpeed > 0;
    }

    public boolean positiveVerticalDirection() {
        return verticalSpeed > 0;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }
}
