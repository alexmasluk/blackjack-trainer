package com.example.alex.testproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {
    public static final String SOFT_17_SWITCH = "soft_17_pref";
    public static final String DOUBLE_SPLIT_SWITCH = "double_after_split_pref";
    public static final String SHOW_VAL_SWITCH = "show_val_pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
