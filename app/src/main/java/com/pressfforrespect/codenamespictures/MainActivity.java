package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
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

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPlay();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectHelp();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSetting();
            }
        });

        Button quit = findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    void selectPlay(){
        Intent myIntent = new Intent(this, RoleSelectActivity.class);
        startActivity(myIntent);
    }

    void selectHelp(){

    }

    void selectSetting(){
        Intent myIntent = new Intent(this, SettingActivity.class);
        startActivity(myIntent);
    }
}
