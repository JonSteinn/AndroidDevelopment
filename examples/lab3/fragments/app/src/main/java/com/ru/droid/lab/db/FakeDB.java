package com.ru.droid.lab.db;

import com.ru.droid.lab.R;

import java.util.HashMap;
import java.util.Map;

public final class FakeDB {
    private static Map<Integer, Data> screenshotIDs;
    private static void init() {
        if (screenshotIDs == null) {
            screenshotIDs = new HashMap<>();
            screenshotIDs.put(R.id.btn_carma, new Data(R.drawable.carmageddon_ss, "Carmageddon"));
            screenshotIDs.put(R.id.btn_jazz, new Data(R.drawable.jazz_jackrabbit2_ss, "Jazz Jackrabbit 2"));
            screenshotIDs.put(R.id.btn_mdk, new Data(R.drawable.mdk_ss, "MDK"));
            screenshotIDs.put(R.id.btn_dung, new Data(R.drawable.dungeon_keeper_ss, "Dungeon Keeper"));
        }
    }
    public static Iterable<Integer> getButtons() {
        init();
        return screenshotIDs.keySet();
    }
    public static Data getScreenshot(int id) {
        init();
        return screenshotIDs.get(id);
    }
    private FakeDB() {}
}
