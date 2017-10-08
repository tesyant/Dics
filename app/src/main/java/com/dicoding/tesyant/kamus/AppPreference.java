package com.dicoding.tesyant.kamus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by tesyant on 10/5/17.
 */

public class AppPreference {

    SharedPreferences preferences;
    Context context;

    public AppPreference(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }


    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getResources().getString(R.string.app_name);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getFirstRun() {
        String key = context.getResources().getString(R.string.app_name);
        return preferences.getBoolean(key, true);
    }
}