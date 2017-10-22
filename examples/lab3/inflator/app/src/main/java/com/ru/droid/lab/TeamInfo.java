package com.ru.droid.lab;

public final class TeamInfo {
    private String name;
    private String eplTitles;
    private String clTitles;
    public TeamInfo(String name, int epl, int cl) {
        this.name = name;
        this.eplTitles = Integer.toString(epl);
        this.clTitles = Integer.toString(cl);
    }
    public String getName() {
        return this.name;
    }
    public String getEplTitles() {
        return this.eplTitles;
    }
    public String getClTitles() {
        return this.clTitles;
    }
}