package com.pressfforrespect.codenamespictures;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.pressfforrespect.codenamespictures.game.Board;

public class SpyMasterActivity extends GameActivity{

    private Board board;

    class CardAdapter extends ImageAdapter{

        CardAdapter(Context context){
            super(context);
        }

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

        CardAdapter cardAdapter = new CardAdapter(this);
        for(int i = 0; i < 20; i++){
            cardAdapter.boardCards[i] = cardAdapter.cardId[board.getPicNums().get(i)];
        }
        cards.setAdapter(cardAdapter);


    }

    @Override
    void gamePause() {

    }

    @Override
    void endTurn() {

    }
}
