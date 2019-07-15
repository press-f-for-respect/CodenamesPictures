package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoleSelectActivity extends AppCompatActivity {

    private Button spyMaster;
    private Button fieldOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);

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
        Intent myIntent = new Intent(this, SpyMasterActivity.class);
        startActivity(myIntent);

    }

    private void selectFieldOperator(){
        Intent myIntent = new Intent(this, FieldOperatorActivity.class);
        startActivity(myIntent);
    }
}
