package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button play;
    private Button help;
    private Button setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        play = findViewById(R.id.play);
        help = findViewById(R.id.help);
        setting = findViewById(R.id.setting);
    }

    void selectPlay(){

    }

    void selectHelp(){

    }

    void selectSetting(){

    }
}
