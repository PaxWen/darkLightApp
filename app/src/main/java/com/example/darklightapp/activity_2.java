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

import com.example.darklightapp.lib.LightThemeManager;

public class activity_2 extends AppCompatActivity {
    final String preferenceName = "preference";
    final String preferenceSavedThemeName = "preferenceSavedTheme";
    final String selectThemeString = "Выбранная тема: ";
    final String systemThemeNowString = "Системная тема: ";

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
                        LightThemeManager.setTheme(activity_2.this,LightThemeManager.SaveThemeFromPreference.THEME_LIGHT);
                        break;
                    case(R.id.darkThemeRadioButton):
                        LightThemeManager.setTheme(activity_2.this,LightThemeManager.SaveThemeFromPreference.THEME_DARK);
                        break;
                    case(R.id.saveBatteryThemeRadioButton):
                        LightThemeManager.setTheme(activity_2.this,LightThemeManager.SaveThemeFromPreference.THEME_BATTERY);
                        break;
                    case(R.id.systemThemeRadioButton):
                        LightThemeManager.setTheme(activity_2.this,LightThemeManager.SaveThemeFromPreference.THEME_SYSTEM);
                        break;
                }
            }
        });
        initTheme();
    }
    private void setSystemStatusTheme(){
        int currentNightMode = LightThemeManager.getThemeStatus(this).getId();
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
        LightThemeManager.SaveThemeFromPreference saveThemeFromPreference = LightThemeManager.getSavedTheme(this);
        if(saveThemeFromPreference== LightThemeManager.SaveThemeFromPreference.THEME_LIGHT){
            themeRadioGroup.check(R.id.lightThemeRadioButton);
        }
        if(saveThemeFromPreference== LightThemeManager.SaveThemeFromPreference.THEME_DARK){
            themeRadioGroup.check(R.id.darkThemeRadioButton);
        }
        if(saveThemeFromPreference== LightThemeManager.SaveThemeFromPreference.THEME_SYSTEM){
            themeRadioGroup.check(R.id.systemThemeRadioButton);
        }
        if(saveThemeFromPreference== LightThemeManager.SaveThemeFromPreference.THEME_BATTERY){
            themeRadioGroup.check(R.id.saveBatteryThemeRadioButton);
        }
        setSystemStatusTheme();
    }

    private void initView(){
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);
        themeStatusTV = findViewById(R.id.statusThemeTextView);
        systemStatusThemeTV = findViewById(R.id.systemStatusThemeTextView);
        themeRadioGroup = findViewById(R.id.themeSwitherRadioGroup);
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