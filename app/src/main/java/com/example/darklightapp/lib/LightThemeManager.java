package com.example.darklightapp.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import static android.content.Context.MODE_PRIVATE;

public class LightThemeManager {

    final static String preferenceName = "preference_LightThemeManager";
    final static String preferenceSavedThemeName = "preferenceSavedTheme_LightThemeManager";

    public enum ThemeStatus{
        MODE_NIGHT_NO(Configuration.UI_MODE_NIGHT_NO),
        MODE_NIGHT_YES(Configuration.UI_MODE_NIGHT_YES),
        NIGHT_UNDEFINED(Configuration.UI_MODE_NIGHT_UNDEFINED);
        private int id;

        ThemeStatus(int id){
            this.id = id;
        }

        public int getId(){
            return this.id;
        }

        public static ThemeStatus fromInt(int id){
            for(ThemeStatus b: ThemeStatus.values()){
                if(b.id == id){
                    return b;
                }
            }
            return null;
        }
    }

    public enum SaveThemeFromPreference{
        THEME_UNDEFINED (0),
        THEME_LIGHT (AppCompatDelegate.MODE_NIGHT_NO),
        THEME_DARK(AppCompatDelegate.MODE_NIGHT_YES),
        THEME_BATTERY(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY),
        THEME_SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        private int id;
        SaveThemeFromPreference(int id){
            this.id = id;
        }

        public int getId(){
            return this.id;
        }

        public static SaveThemeFromPreference fromInt(int id){
            for(SaveThemeFromPreference b: SaveThemeFromPreference.values()){
                if(b.id == id){
                    return b;
                }
            }
            return null;
        }
    }

    public static SaveThemeFromPreference getSavedTheme(Context context){
        SharedPreferences preferences = context.getSharedPreferences(preferenceName,MODE_PRIVATE);
        return SaveThemeFromPreference.fromInt(preferences.getInt(preferenceSavedThemeName,0));
    }

    public static void setSaveTheme(Context context){
        SaveThemeFromPreference savePref = getSavedTheme(context);
        if(savePref!=SaveThemeFromPreference.THEME_UNDEFINED){
            setTheme(context,SaveThemeFromPreference.THEME_SYSTEM);
        }else{
            setTheme(context,savePref);
        }
    }
    public static ThemeStatus getThemeStatus(Context context){
        int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return ThemeStatus.fromInt(currentNightMode);
    }
    private static void saveModeTheme(Context context,SaveThemeFromPreference mode){
        SharedPreferences preferences = context.getSharedPreferences(preferenceName,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(preferenceSavedThemeName,mode.getId());
        editor.apply();
    }

    public static void setTheme(Context context,SaveThemeFromPreference prefsMode){
        AppCompatDelegate.setDefaultNightMode(prefsMode.getId());
        saveModeTheme(context,prefsMode);
    }

}
