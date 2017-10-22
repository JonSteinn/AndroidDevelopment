package com.ru.droid.lab;

import java.util.HashMap;
import java.util.Map;

public final class FakeDB {
    private static Map<Integer, TeamInfo> imageDetail;
    private static void init() {
        if (imageDetail == null) {
            imageDetail = new HashMap<>();
            imageDetail.put(R.drawable.afc, new TeamInfo("Arsenal FC", 13, 0));
            imageDetail.put(R.drawable.lfc, new TeamInfo("Liverpool FC", 18, 5));
            imageDetail.put(R.drawable.mufc, new TeamInfo("Manchester United", 20, 3));
        }
    }
    public static Iterable<Map.Entry<Integer, TeamInfo>> getAllImages() {
        init();
        return imageDetail.entrySet();
    }
}
