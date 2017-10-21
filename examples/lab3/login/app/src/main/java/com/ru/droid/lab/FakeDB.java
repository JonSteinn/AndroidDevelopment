package com.ru.droid.lab;

import java.util.HashMap;
import java.util.Map;

public final class FakeDB {
    private static Map<String, String> usersAndPasswords = new HashMap<>();
    static {
        usersAndPasswords.put("Max", "abcd");
        usersAndPasswords.put("Mona", "1234");
        usersAndPasswords.put("Vladimir", "Vodka");
        usersAndPasswords.put("Nicole", "PV");
        usersAndPasswords.put("Alfred", "IC");
    }
    public static boolean doesExist(String un) {
        return usersAndPasswords.containsKey(un);
    }
    public static boolean correctPassword(String un, String pw) {
        return usersAndPasswords.containsKey(un) && usersAndPasswords.get(un).equals(pw);
    }
    private FakeDB() {}
}
