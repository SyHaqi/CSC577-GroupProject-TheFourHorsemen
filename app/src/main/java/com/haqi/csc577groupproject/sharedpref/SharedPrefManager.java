package com.haqi.csc577groupproject.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

import com.haqi.csc577groupproject.model.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "socialgoodvolunteersharedpref";
    private static final String KEY_ID       = "keyid";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL    = "keyemail";
    private static final String KEY_TOKEN    = "keytoken";
    private static final String KEY_ROLE     = "keyrole";
    private static final String KEY_PHONE    = "keyphone";

    private final Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public void storeUser(User user) {
        SharedPreferences.Editor editor = mCtx
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL,    user.getEmail());
        editor.putString(KEY_TOKEN,    user.getToken());
        editor.putString(KEY_ROLE,     user.getRole());
        editor.putString(KEY_PHONE,    user.getPhone());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(KEY_USERNAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sp = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = new User();
        user.setId(sp.getInt(KEY_ID, -1));
        user.setUsername(sp.getString(KEY_USERNAME, null));
        user.setEmail(sp.getString(KEY_EMAIL,    null));
        user.setToken(sp.getString(KEY_TOKEN,    null));
        user.setRole(sp.getString(KEY_ROLE,     null));
        user.setPhone(sp.getString(KEY_PHONE,    null));
        return user;
    }

    public void logout() {
        mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit().clear().apply();
    }
}
