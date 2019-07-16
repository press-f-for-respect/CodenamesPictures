package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.textfield.TextInputEditText;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private CheckBox soundCheckBox;
    private CheckBox musicCheckBox;
    private TextInputEditText deviceName;
    final static public String KEY = "string";

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedPref = getSharedPreferences(KEY, Context.MODE_PRIVATE);

        soundCheckBox = findViewById(R.id.sound_check);
        soundCheckBox.setChecked(sharedPref.getBoolean(String.valueOf(R.id.sound_check), false));

        musicCheckBox = findViewById(R.id.music_check);
        musicCheckBox.setChecked(sharedPref.getBoolean(String.valueOf(R.id.music_check), false));
        musicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                BackgroundMusic.setDoPlay(b);
                if(b)
                    BackgroundMusic.getInstance().play();
                else
                    BackgroundMusic.getInstance().pause();
            }
        });


        deviceName = findViewById(R.id.input_device_name);
        deviceName.setText(sharedPref.getString(String.valueOf(R.id.input_device_name), Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).substring(0,5)));


    }

    private void putInSharedPref(){
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean(String.valueOf(R.id.sound_check), soundCheckBox.isChecked());
        editor.putBoolean(String.valueOf(R.id.music_check), musicCheckBox.isChecked());
        editor.putString(String.valueOf(R.id.input_device_name), deviceName.getText().toString());

        editor.commit();
    }

    @Override
    protected void onDestroy() {
        putInSharedPref();
        super.onDestroy();
    }

}
