package com.example.darklightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class activity_2 extends AppCompatActivity {
    final String preferenceName = "preference";
    final String preferenceSavedThemeName = "preferenceSavedTheme";
    final String selectThemeString = "Выбранная тема: ";
    final String systemThemeNowString = "Системная тема: ";

    final int THEME_UNDEFINED = -1;
    final int THEME_LIGHT = 0;
    final int THEME_DARK = 1;
    final int THEME_BATTERY = 2;
    final int THEME_SYSTEM = 3;

    Button button1;
    Button button2;
    Button button3;
    TextView themeStatusTV;
    TextView systemStatusThemeTV;
    RadioGroup themeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        initView();

        themeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case(R.id.lightThemeRadioButton):
                        Toast.makeText(getApplicationContext(),"Click on lightTheme",Toast.LENGTH_SHORT).show();
                        setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT);
                        break;
                    case(R.id.darkThemeRadioButton):
                        Toast.makeText(getApplicationContext(),"Click on darkTheme",Toast.LENGTH_SHORT).show();
                        setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK);
                        break;
                    case(R.id.saveBatteryThemeRadioButton):
                        Toast.makeText(getApplicationContext(),"Click on saveBatteryTheme",Toast.LENGTH_SHORT).show();
                        setTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY, THEME_BATTERY);
                        break;
                    case(R.id.systemThemeRadioButton):
                        Toast.makeText(getApplicationContext(),"Click on systemTheme",Toast.LENGTH_SHORT).show();
                        setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, THEME_SYSTEM);
                        break;
                }
            }
        });
        initTheme();
    }
    private void setSystemStatusTheme(){
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                systemStatusThemeTV.setText(systemThemeNowString + "Светлая");
            case Configuration.UI_MODE_NIGHT_YES:
                systemStatusThemeTV.setText(systemThemeNowString + "Тёмная");
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                systemStatusThemeTV.setText(systemThemeNowString + "Неизвестно");
        }
    }
    private void initTheme(){
        int mode = getSavedTheme();
        switch (mode){
            case THEME_LIGHT:
                themeRadioGroup.check(R.id.lightThemeRadioButton);
                break;
            case THEME_DARK:
                themeRadioGroup.check(R.id.darkThemeRadioButton);
                break;
            case THEME_SYSTEM:
                themeRadioGroup.check(R.id.systemThemeRadioButton);
                break;
            case THEME_BATTERY:
                themeRadioGroup.check(R.id.saveBatteryThemeRadioButton);
                break;
            case THEME_UNDEFINED:

                break;
        }

    }

    private void initView(){
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);
        themeStatusTV = findViewById(R.id.statusThemeTextView);
        systemStatusThemeTV = findViewById(R.id.systemStatusThemeTextView);
        themeRadioGroup = findViewById(R.id.themeSwitherRadioGroup);
    }

    private int getSavedTheme(){
        SharedPreferences preferences = getSharedPreferences(preferenceName,MODE_PRIVATE);
        return preferences.getInt(preferenceSavedThemeName,-1);
    }
    private void saveModeTheme(int mode){
        SharedPreferences preferences = getSharedPreferences(preferenceName,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(preferenceSavedThemeName,mode);
        editor.apply();
    }

    private void setTheme(int themeMode, int prefsMode){
        AppCompatDelegate.setDefaultNightMode(themeMode);
        switch (prefsMode){
            case THEME_LIGHT:
                themeStatusTV.setText(selectThemeString+"Светлая");
                break;
            case THEME_DARK:
                themeStatusTV.setText(selectThemeString+"Тёмная");
                break;
            case THEME_SYSTEM:
                themeStatusTV.setText(selectThemeString+"Системы");
                break;
            case THEME_BATTERY:
                themeStatusTV.setText(selectThemeString+"Батареи");
                break;
        }
        saveModeTheme(prefsMode);
        setSystemStatusTheme();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                systemStatusThemeTV.setText(systemThemeNowString + "Светлая");
            case Configuration.UI_MODE_NIGHT_YES:
                systemStatusThemeTV.setText(systemThemeNowString + "Тёмная");
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                systemStatusThemeTV.setText(systemThemeNowString + "Неизвестно");
        }
    }
}