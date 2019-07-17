package com.pressfforrespect.codenamespictures;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.pressfforrespect.codenamespictures.Animation.FlipAnimation;
import com.pressfforrespect.codenamespictures.game.Board;
import com.pressfforrespect.codenamespictures.game.Team;

public class FieldOperatorActivity extends GameActivity {

    private Board board;
    private CountDownTimer timer;
    private long timeUntilFinished;
    private boolean[] clicked = new boolean[20];

    class CardAdapter extends GameActivity.ImageAdapter {

        CardAdapter(Context context){
            super(context);
        }

        @SuppressLint({"ResourceAsColor", "ResourceType"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            CardView card;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(view == null){
                View gridView = inflater.inflate(R.layout.card_layout, null);
                card = gridView.findViewById(R.id.card_element);
                card.setLayoutParams(new GridView.LayoutParams(width/11,width/11));

            }else{
                card = (CardView) view;
            }
            if(!clicked[i]) {
                ((ImageView)((FrameLayout) card.getChildAt(0)).getChildAt(0)).setImageResource(boardCards[i]);
            } else {
                switch (board.getTeam()[i]) {
                    case RED:
                        ((ImageView)((FrameLayout) card.getChildAt(0)).getChildAt(0)).setImageResource(R.drawable.red_team);
                        break;
                    case BLUE:
                        ((ImageView)((FrameLayout) card.getChildAt(0)).getChildAt(0)).setImageResource(R.drawable.blue_team);
                        break;
                    case BYSTANDER:
                        ((ImageView)((FrameLayout) card.getChildAt(0)).getChildAt(0)).setImageResource(R.drawable.bystander);
                        break;
                    case ASSASSIN:
                        ((ImageView)((FrameLayout) card.getChildAt(0)).getChildAt(0)).setImageResource(R.drawable.assasin);
                        break;
                    default:
                        ((ImageView)((FrameLayout) card.getChildAt(0)).getChildAt(0)).setImageResource(R.drawable.bystander);
                        break;
                }
            }

            return card;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        board = new Board();
        super.changeColor(board.getStarter());


        FieldOperatorActivity.CardAdapter cardAdapter = new FieldOperatorActivity.CardAdapter(this);
        for(int i = 0; i < 20; i++){
            cardAdapter.boardCards[i] = cardAdapter.cardId[board.getPicNums().get(i)];
        }

        final boolean playSound = getSharedPreferences(SettingActivity.KEY, Context.MODE_PRIVATE).getBoolean(String.valueOf(R.id.sound_check), false);

        cards.setAdapter(cardAdapter);
        cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(!clicked[i]) {
                    cardClicked((CardView) view, i);
                    if (playSound) {
                        //TODO change sound
                        MediaPlayer sound = MediaPlayer.create(FieldOperatorActivity.this, R.raw.fart);
                        sound.setLooping(false);
                        sound.setVolume(100, 100);
                        sound.start();
                    }
                    clicked[i] = true;
                }

            }
        });

        sideButton.setText(R.string.end_turn);

//        cards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                return true;
//            }
//        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePause();
            }
        });

        sideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTurn();
            }
        });

        timer = new CountDownTimer(120000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                pause.setText(Integer.toString((int) (millisUntilFinished/1000)));
                timeUntilFinished = millisUntilFinished;
            }

            public void onFinish() {
                endTurn();
            }
        };
        timer.start();

        discreteSeekBar.setVisibility(View.GONE);
        description.setVisibility(View.GONE);

    }

    @Override
    void gamePause() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!isPaused) {
            transaction.add(R.id.pause_container, new PauseFragment(this), PAUSE_FRAGMENT_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
            pauseLayout.setVisibility(View.VISIBLE);
            cards.setVisibility(View.GONE);
            sideButton.setVisibility(View.GONE);
            timer.cancel();
        }else{
            transaction.remove(getSupportFragmentManager().findFragmentByTag(PAUSE_FRAGMENT_TAG)).commit();
            pauseLayout.setVisibility(View.GONE);
            cards.setVisibility(View.VISIBLE);
            sideButton.setVisibility(View.VISIBLE);

            timer = new CountDownTimer(timeUntilFinished, 1000) {

                @SuppressLint("SetTextI18n")
                public void onTick(long millisUntilFinished) {
                    pause.setText(Integer.toString((int) (millisUntilFinished/1000)));
                    timeUntilFinished = millisUntilFinished;
                }

                public void onFinish() {
                    endTurn();
                }
            };
            timer.start();
        }
        isPaused = !isPaused;
    }

    @Override
    void endTurn() {
        board.endTurn();
        changeColor(board.getStarter());

        timer.cancel();
        timer = new CountDownTimer(120000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                pause.setText(Integer.toString((int) (millisUntilFinished/1000)));
                timeUntilFinished = millisUntilFinished;
            }

            public void onFinish() {
                endTurn();
            }
        };
        timer.start();
    }

    @Override
    void endGame() {

    }

    @SuppressLint("ResourceType")
    private void cardClicked(CardView card, int i){

        Team cardTeam =  board.getTeam()[i];



        FlipAnimation flipAnimation = new FlipAnimation(card, cardTeam);
        card.startAnimation(flipAnimation);

        if(cardTeam == Team.ASSASSIN)
            endGame();
        else if(board.getStarter() != cardTeam)
            endTurn();
    }
}
