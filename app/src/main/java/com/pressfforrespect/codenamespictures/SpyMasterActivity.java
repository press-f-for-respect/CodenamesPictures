package com.pressfforrespect.codenamespictures;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import com.pressfforrespect.codenamespictures.game.Board;


public class SpyMasterActivity extends GameActivity{

    private Board board;
    final static private String PAUSE_FRAGMENT_TAG = "Pause";

    class CardAdapter extends ImageAdapter{

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

                switch (board.getTeam()[i]){
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


        CardAdapter cardAdapter = new CardAdapter(this);
        for(int i = 0; i < 20; i++){
            cardAdapter.boardCards[i] = cardAdapter.cardId[board.getPicNums().get(i)];
        }
        cards.setAdapter(cardAdapter);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePause();
            }
        });

        sideButton.setText(R.string.spymaster_turn);

        sideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        dummyView.setVisibility(View.GONE);


    }

    @Override
    void gamePause() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!isPaused) {
            transaction.add(R.id.container, new PauseFragment(this), PAUSE_FRAGMENT_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
            pauseLayout.setVisibility(View.VISIBLE);
            cards.setVisibility(View.GONE);
            sideButton.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
            discreteSeekBar.setVisibility(View.GONE);
        }else{
            transaction.remove(getSupportFragmentManager().findFragmentByTag(PAUSE_FRAGMENT_TAG)).commit();
            pauseLayout.setVisibility(View.GONE);
            cards.setVisibility(View.VISIBLE);
            sideButton.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            discreteSeekBar.setVisibility(View.VISIBLE);
        }
        isPaused = !isPaused;
    }

    @Override
    void endTurn() {
        board.endTurn();
        super.changeColor(board.getStarter());
    }

    @Override
    void endGame() {

    }

}
