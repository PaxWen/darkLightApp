package com.example.darklightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darklightapp.lib.LightThemeManager;

public class MainActivity extends AppCompatActivity {
    Button nextActivityButton;
    TextView systemStatusThemeTV;
    final String systemThemeNowString = "Системная тема: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        nextActivityButton.setOnClickListener(v->{
            toNextActivity();
        });
        setSystemStatusTheme();
    }
    private void toNextActivity(){
        Intent myIntent = new Intent(this, activity_2.class);
        this.startActivity(myIntent);
    }
    private void initView(){
        nextActivityButton = findViewById(R.id.button5);
        systemStatusThemeTV = findViewById(R.id.systemStatusThemeTextView);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setSystemStatusTheme();
    }
}