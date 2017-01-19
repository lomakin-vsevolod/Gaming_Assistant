package com.by.lomakin.gaming_assistant.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 23699 on 21.11.2016.
 */

public class VkAuthUtils {
    private static final String VK_ACCESS_TOKEN_KEY = "VK_ACCESS_TOKEN";
    private static final String VK_USER_ID = "VK_USER_ID";

    public static final String PREFS_NAME = "SharedPreferences";

    private Context context;

    public VkAuthUtils(Context ctx) {
        if (ctx != null) {
            context = ctx.getApplicationContext();
        }
    }

    public boolean checkTokenInSharedPreferences() {
        String accessToken = getTokenFromSharedPreferences();
        if (accessToken != null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveTokenToSharedPreferences(String accessToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VK_ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }

    public String getTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        return sharedPreferences.getString(VK_ACCESS_TOKEN_KEY, null);
    }

    public void saveUserIdToSharedPreferences(String userId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VK_USER_ID, userId);
        editor.apply();
    }

    public String getUserIdFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        return sharedPreferences.getString(VK_USER_ID, null);
    }
    public void deleteSharedPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
