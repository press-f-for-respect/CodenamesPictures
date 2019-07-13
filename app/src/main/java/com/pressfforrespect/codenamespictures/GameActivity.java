package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pressfforrespect.codenamespictures.game.Team;

import java.util.ArrayList;

public abstract class GameActivity extends AppCompatActivity {

    protected Button pause;
    protected TextView timer;
    private CardView sideBar;
    protected GridView cards;

    class ImageAdapter extends BaseAdapter{

        private Context context;

        ImageAdapter(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        cards = findViewById(R.id.cards_view);

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