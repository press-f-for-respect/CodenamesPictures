package com.pressfforrespect.codenamespictures;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import com.pressfforrespect.codenamespictures.game.Board;
import com.pressfforrespect.codenamespictures.game.Team;


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

            ImageView cardImage = (ImageView)((FrameLayout) card.getChildAt(0)).getChildAt(0);
            cardImage.setImageResource(boardCards[i]);
            cardImage.setAlpha((float) 0.5);

            return card;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        board = new Board();
        changeColor(board.getStarter());


        final CardAdapter cardAdapter = new CardAdapter(this);
        for(int i = 0; i < 20; i++){
            cardAdapter.boardCards[i] = cardAdapter.cardId[board.getPicNums().get(i)];
        }
        cards.setAdapter(cardAdapter);
        cards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @SuppressLint("ResourceType")
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((ImageView) previewLayout.getChildAt(1)).setImageResource(
                        cardAdapter.boardCards[i]);
                previewLayout.setVisibility(View.VISIBLE);
                AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(200);
                anim.setRepeatCount(0);
                anim.setRepeatMode(Animation.REVERSE);
                previewLayout.startAnimation(anim);

                switch (board.getTeam()[i]){
                    case RED:
                        blurLayout.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorRed)));
                        break;
                    case BLUE:
                        blurLayout.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorBlue)));
                        break;
                    case ASSASSIN:
                        blurLayout.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorBlack)));
                        break;
                    case BYSTANDER:
                        blurLayout.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorCream)));
                        break;
                    default:
                        blurLayout.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorCream)));
                }
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

        dummyView.setVisibility(View.GONE);


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
            description.setVisibility(View.GONE);
            discreteSeekBar.setVisibility(View.GONE);
        }else{
            transaction.remove(getSupportFragmentManager().findFragmentByTag(PAUSE_FRAGMENT_TAG)).commit();
            extraLayout.setVisibility(View.GONE);
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
        showToast(board.getStarter());
        super.changeColor(board.getStarter());
        changeColor(board.getStarter());
    }

    @Override
    void endGame(final Team team) {
        isEnded = true;
        cards.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                GameEndFragment gameEnd = new GameEndFragment(SpyMasterActivity.this);
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

}
