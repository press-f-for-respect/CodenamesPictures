package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pressfforrespect.codenamespictures.game.Team;

import java.util.ArrayList;

public abstract class GameActivity extends AppCompatActivity {

    private Button pause;
    private TextView timer;
    private CardView sideBar;
    private ArrayList<ImageView> cards = new ArrayList<>();

    private int id[] = {R.id.card_1, R.id.card_2, R.id.card_3, R.id.card_4, R.id.card_5, R.id.card_6,
            R.id.card_7, R.id.card_8, R.id.card_9, R.id.card_10, R.id.card_11, R.id.card_12, R.id.card_13,
            R.id.card_14, R.id.card_15, R.id.card_16, R.id.card_17, R.id.card_18, R.id.card_19, R.id.card_20};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        for (int i = 0; i < 20; i++)
            cards.add((ImageView) findViewById(id[i]));

        pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePause();
            }
        });

        timer = findViewById(R.id.timer);
        sideBar = findViewById(R.id.side_bar);

    }

    @SuppressLint("ResourceAsColor")
    public void changeColor(Team team){
        if(team == Team.BLUE)
            sideBar.setBackgroundColor(R.color.colorBlue);
        else
            sideBar.setBackgroundColor(R.color.colorRed);

    }

    abstract void gamePause();

    abstract void endTurn();
}
