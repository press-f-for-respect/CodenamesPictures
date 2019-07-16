package com.pressfforrespect.codenamespictures;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoleSelectActivity extends AppCompatActivity {

    private Button spyMaster;
    private Button fieldOperator;
    private boolean clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        spyMaster = findViewById(R.id.spymaster_butt);
        spyMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSpyMaster();
            }
        });

        fieldOperator = findViewById(R.id.field_op);
        fieldOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFieldOperator();
            }
        });
    }

    private void selectSpyMaster(){
        clicked = true;
        Intent myIntent = new Intent(this, WifiActivity.class);
        startActivity(myIntent);

    }

    private void selectFieldOperator(){
        clicked = true;
        Intent myIntent = new Intent(this, FieldOperatorActivity.class);
        startActivity(myIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!clicked && BackgroundMusic.getInstance() != null)
            BackgroundMusic.getInstance().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clicked = false;
        if(BackgroundMusic.getInstance() != null)
            BackgroundMusic.getInstance().play();
//        else
            //TODO change music
//            BackgroundMusic.getInstance(this, R.raw.ykc).play();
    }

    @Override
    public void onBackPressed() {
        clicked = true;
        super.onBackPressed();
    }
}
