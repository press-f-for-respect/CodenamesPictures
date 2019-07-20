package com.pressfforrespect.codenamespictures;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import com.pressfforrespect.codenamespictures.Animation.FlipAnimation;
import com.pressfforrespect.codenamespictures.game.Board;
import com.pressfforrespect.codenamespictures.game.Team;

public class FieldOperatorActivity extends GameActivity {

    private Board board;
    private CountDownTimer timer;
    private long timeUntilFinished;
    private boolean[] clicked = new boolean[20];
    private int numOfMoves = -1;

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
        changeColor(board.getStarter());


        final FieldOperatorActivity.CardAdapter cardAdapter = new FieldOperatorActivity.CardAdapter(this);
        for(int i = 0; i < 20; i++){
            cardAdapter.boardCards[i] = cardAdapter.cardId[board.getPicNums().get(i)];
        }

        final boolean playSound = getSharedPreferences(SettingActivity.KEY, Context.MODE_PRIVATE).getBoolean(String.valueOf(R.id.sound_check), false);

        cards.setAdapter(cardAdapter);
        cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(!clicked[i]) {
                    if(numOfMoves != -1){
                        cardClicked((CardView) view, i);
                        clicked[i] = true;
                        if (playSound) {
                            MediaPlayer sound = MediaPlayer.create(FieldOperatorActivity.this, R.raw.card_flip);
                            sound.setLooping(false);
                            sound.setVolume(100, 100);
                            sound.start();
                        }
                    }else {
                        Toast.makeText(FieldOperatorActivity.this, "Number of moves hasn't been specified !", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        cards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView card = (ImageView) previewLayout.getChildAt(1);
                card.setImageResource(cardAdapter.cardId[board.getPicNums().get(i)]);
                card.setElevation(20);
                previewLayout.setVisibility(View.VISIBLE);
                AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(200);
                anim.setRepeatCount(0);
                anim.setRepeatMode(Animation.REVERSE);
                previewLayout.startAnimation(anim);

                blurLayout.startBlur();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        blurLayout.pauseBlur();
                    }
                }, 100);
                return true;
            }
        });

        sideButton.setText(R.string.end_turn);

        previewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                anim.setDuration(200);
                anim.setRepeatCount(0);
                anim.setRepeatMode(Animation.REVERSE);
                previewLayout.startAnimation(anim);
                previewLayout.setVisibility(View.GONE);
            }
        });

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
            transaction.add(R.id.container, new PauseFragment(this), PAUSE_FRAGMENT_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
            extraLayout.setVisibility(View.VISIBLE);
            cards.setVisibility(View.GONE);
            sideButton.setVisibility(View.GONE);
            timer.cancel();
        }else{
            transaction.remove(getSupportFragmentManager().findFragmentByTag(PAUSE_FRAGMENT_TAG)).commit();
            extraLayout.setVisibility(View.GONE);
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
        showToast(board.getStarter());
        changeColor(board.getStarter());
        numOfMoves = -1;


        if (getSharedPreferences(SettingActivity.KEY, Context.MODE_PRIVATE).getBoolean(String.valueOf(R.id.sound_check), false)) {
            MediaPlayer sound = MediaPlayer.create(FieldOperatorActivity.this, R.raw.change);
            sound.setLooping(false);
            sound.setVolume(100, 100);
            sound.start();
        }

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

    @SuppressLint("ResourceType")
    @Override
    void endGame(final Team team) {
        isEnded = true;
        timer.cancel();
        cards.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                GameEndFragment gameEnd = new GameEndFragment(FieldOperatorActivity.this);
                gameEnd.setWinner(team);
                transaction.add(R.id.container, gameEnd, END_FRAGMENT_TAG);
                transaction.addToBackStack(null);
                transaction.commit();
                extraLayout.setVisibility(View.VISIBLE);
                cards.setVisibility(View.GONE);
                sideButton.setVisibility(View.GONE);
                pause.setVisibility(View.GONE);
            }
        }, 1000);
    }

    @SuppressLint("ResourceType")
    private void cardClicked(CardView card, int i){
        numOfMoves -= 1;

        Team cardTeam =  board.getTeam()[i];
        int state = board.reduceNumOfTeamCards(cardTeam);

        FlipAnimation flipAnimation = new FlipAnimation(card, cardTeam);
        card.startAnimation(flipAnimation);

        if(cardTeam == Team.ASSASSIN || state != 0) {
            if (board.getStarter() == Team.RED || state == 2)
                endGame(Team.BLUE);
            else if (board.getStarter() == Team.BLUE || state == 1)
                endGame(Team.RED);
        } else if(board.getStarter() != cardTeam || numOfMoves == 0)
            endTurn();

    }

    public void setNumOfMoves(int numOfMoves) {
        if(numOfMoves != 0)
            this.numOfMoves = numOfMoves;
        else
            this.numOfMoves = -2;
    }
}
