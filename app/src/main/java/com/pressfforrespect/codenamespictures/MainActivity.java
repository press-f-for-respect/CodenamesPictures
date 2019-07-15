package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button play;
    private Button help;
    private Button setting;
    private boolean clicked;

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
                alert();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        clicked = false;
        //TODO change menu music
        BackgroundMusic.getInstance(this, R.raw.ykc).play();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!clicked)
            BackgroundMusic.getInstance().pause();
    }

    void selectPlay(){
        clicked = true;
        Intent myIntent = new Intent(this, RoleSelectActivity.class);
        startActivity(myIntent);
    }

    void selectHelp(){
        clicked = true;
    }

    void selectSetting(){
        clicked = true;
        Intent myIntent = new Intent(this, SettingActivity.class);
        startActivity(myIntent);
    }

    private void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
