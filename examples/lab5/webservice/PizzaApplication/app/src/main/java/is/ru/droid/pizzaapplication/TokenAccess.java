package is.ru.droid.pizzaapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenAccess {
    private static final String PREF_NAME = "TOKEN";
    private static final String TOKEN_ACCESS_KEY = "STORED_TOKEN";

    private static TokenAccess INSTANCE = null;
    public static TokenAccess getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TokenAccess.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TokenAccess(context);
                }
            }
        }
        return INSTANCE;
    }

    private SharedPreferences preferences;
    private TokenAccess(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN_ACCESS_KEY, token);
        editor.apply();
    }

    public void clearToken() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(TOKEN_ACCESS_KEY);
        editor.apply();
    }

    public boolean hasToken() {
        return preferences.contains(TOKEN_ACCESS_KEY);
    }

    public String getToken() {
        return preferences.getString(TOKEN_ACCESS_KEY, null);
    }
}
