package com.ru.droid.lab;

public class Data {
    private int screenShotId;
    private String screenShotTitle;
    public Data(int id, String title) {
        screenShotId = id;
        screenShotTitle = title;
    }
    public int getScreenShotId() {
        return screenShotId;
    }
    public String getScreenShotTitle() {
        return screenShotTitle;
    }
}
