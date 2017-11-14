package com.ru.droid.lab;

import java.util.HashMap;
import java.util.Map;

public final class FakeDB {
    private static Map<String, String> usersAndPasswords = new HashMap<>();
    static {
        usersAndPasswords.put("Max", "Payne");
        usersAndPasswords.put("Mona", "Sax");
        usersAndPasswords.put("Vladimir", "Lem");
        usersAndPasswords.put("Nicole", "Horne");
        usersAndPasswords.put("Alfred", "Woden");
        usersAndPasswords.put("Jack", "Lupino");
    }
    public static boolean validate(String un, String pw) {
        return pw != null && pw.equals(usersAndPasswords.get(un));
    }
    private FakeDB() {}
}
