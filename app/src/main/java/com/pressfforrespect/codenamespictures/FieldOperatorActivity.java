package com.pressfforrespect.codenamespictures;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.pressfforrespect.codenamespictures.game.Board;
import com.pressfforrespect.codenamespictures.game.Team;

public class FieldOperatorActivity extends GameActivity {

    private Board board;
    private CountDownTimer timer;
    private long timeUntilFinished;

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

            }else{
                card = (CardView) view;
            }
            ((ImageView)((FrameLayout) card.getChildAt(0)).getChildAt(0)).setImageResource(boardCards[i]);

            return card;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        board = new Board();
        super.changeColor(board.getStarter());

        cards.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        FieldOperatorActivity.CardAdapter cardAdapter = new FieldOperatorActivity.CardAdapter(this);
        for(int i = 0; i < 20; i++){
            cardAdapter.boardCards[i] = cardAdapter.cardId[board.getPicNums().get(i)];
        }

        cards.setAdapter(cardAdapter);
        cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cardClicked((CardView) view, i);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePause();
            }
        });

        endTurnButton.setOnClickListener(new View.OnClickListener() {
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


    }

    @Override
    void gamePause() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!isPaused) {
            transaction.add(R.id.pause_container, new PauseFragment(this), PAUSE_FRAGMENT_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
            pauseLayout.setVisibility(View.VISIBLE);
            timer.cancel();
        }else{
            transaction.remove(getSupportFragmentManager().findFragmentByTag(PAUSE_FRAGMENT_TAG)).commit();
            pauseLayout.setVisibility(View.GONE);

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

        switch (cardTeam){
            case RED:
                card.setCardBackgroundColor(Color.parseColor(getResources().getString(R.color.colorRed)));
                break;
            case BLUE:
                card.setCardBackgroundColor(Color.parseColor(getResources().getString(R.color.colorBlue)));
                break;
            case ASSASSIN:
                card.setCardBackgroundColor(Color.parseColor(getResources().getString(R.color.colorBlack)));
                break;
            case BYSTANDER:
                card.setCardBackgroundColor(Color.parseColor(getResources().getString(R.color.colorCream)));
                break;
            default:
                card.setCardBackgroundColor(Color.parseColor(getResources().getString(R.color.colorCream)));
        }

        if(cardTeam == Team.ASSASSIN)
            endGame();
        else if(board.getStarter() != cardTeam)
            endTurn();
    }
}
