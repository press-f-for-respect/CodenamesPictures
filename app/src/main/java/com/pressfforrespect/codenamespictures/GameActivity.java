package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pressfforrespect.codenamespictures.game.Team;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import io.alterac.blurkit.BlurLayout;


public abstract class GameActivity extends AppCompatActivity {

    protected Button pause;
    protected Button sideButton;
    protected GridView cards;
    protected FrameLayout extraLayout;
    protected LinearLayout gameBar;
    protected Boolean isPaused = false;
    protected Boolean isEnded = false;
    final static public String PAUSE_FRAGMENT_TAG = "Pause";
    final static public String END_FRAGMENT_TAG = "End Game";
    protected int width;
    protected int height;
    protected DiscreteSeekBar discreteSeekBar;
    protected TextView description;
    protected View dummyView;
    protected FrameLayout previewLayout;
    protected BlurLayout blurLayout;

    class ImageAdapter extends BaseAdapter{


        protected Context context;
        protected int[] cardId = { R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5,
                R.drawable.c6, R.drawable.c7, R.drawable.c8, R.drawable.c9, R.drawable.c10,
                R.drawable.c11, R.drawable.c12, R.drawable.c13, R.drawable.c14, R.drawable.c15,
                R.drawable.c16, R.drawable.c17, R.drawable.c18, R.drawable.c19, R.drawable.c20,
                R.drawable.c21, R.drawable.c22, R.drawable.c23, R.drawable.c24, R.drawable.c25,
                R.drawable.c26, R.drawable.c27, R.drawable.c28, R.drawable.c29, R.drawable.c30,
                R.drawable.c31, R.drawable.c32, R.drawable.c33, R.drawable.c34, R.drawable.c35,
                R.drawable.c36, R.drawable.c37, R.drawable.c38, R.drawable.c39, R.drawable.c40,
                R.drawable.c41, R.drawable.c42, R.drawable.c43, R.drawable.c44, R.drawable.c45,
                R.drawable.c46, R.drawable.c47, R.drawable.c48, R.drawable.c49, R.drawable.c50,
                R.drawable.c51, R.drawable.c52, R.drawable.c53, R.drawable.c54, R.drawable.c55,
                R.drawable.c56, R.drawable.c57, R.drawable.c58, R.drawable.c59, R.drawable.c60,
                R.drawable.c61, R.drawable.c62, R.drawable.c63, R.drawable.c64, R.drawable.c65,
                R.drawable.c66, R.drawable.c67, R.drawable.c68, R.drawable.c69, R.drawable.c70,
                R.drawable.c71, R.drawable.c72, R.drawable.c73, R.drawable.c74, R.drawable.c75,
                R.drawable.c76, R.drawable.c77, R.drawable.c78, R.drawable.c79, R.drawable.c80};

        protected int[] boardCards = new int[20];


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

        cards.setLongClickable(true);

        previewLayout = findViewById(R.id.preview_layout);
        previewLayout.setVisibility(View.GONE);

        blurLayout = findViewById(R.id.blur_layout);
        blurLayout.setDownscaleFactor(0.5F);

        pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePause();
            }
        });

        extraLayout = findViewById(R.id.container);
        extraLayout.setVisibility(View.GONE);

        sideButton = findViewById(R.id.end_turn);

        gameBar = findViewById(R.id.game_bar);

        discreteSeekBar = findViewById(R.id.guess_seekbar);

        description = findViewById(R.id.seekbar_description);

        dummyView = findViewById(R.id.dummy_view);

        setView();

//        BackgroundMusic.getInstance().stop();
        //TODO change game music
//        BackgroundMusic.getInstance(this, R.raw.ykc).play();

    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    public void changeColor(Team team){
        if(team == Team.BLUE) {
            pause.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorBlue)));
            extraLayout.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorBlue)));
        }else {
            pause.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorRed)));
            extraLayout.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorRed)));
        }

    }

    abstract void gamePause();

    abstract void endTurn();

    abstract void endGame(Team team);

    protected void finishGame(){
//        BackgroundMusic.getInstance().stop();
        finish();
    }

    @Override
    public void onBackPressed() {
        if(!isEnded)
            gamePause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(BackgroundMusic.getInstance() != null && !isPaused)
            BackgroundMusic.getInstance().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(BackgroundMusic.getInstance() != null)
            BackgroundMusic.getInstance().play();
    }

    void setView(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        LinearLayout.LayoutParams gridLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gridLayout.setMargins(width/5,height/40,width/5,height/40);
        cards.setLayoutParams(gridLayout);
        cards.setVerticalSpacing(height/25);

        LinearLayout.LayoutParams barLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        barLayout.setMargins(width/60,height/60,width/60,0);
        gameBar.setLayoutParams(barLayout);
    }

    void showToast(Team teamTurn) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = teamTurn == Team.RED? inflater.inflate(R.layout.toast_layout_red, (ViewGroup)findViewById(R.id.toast_layout_red)) :
                inflater.inflate(R.layout.toast_layout_blue, (ViewGroup)findViewById(R.id.toast_layout_blue));


        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}