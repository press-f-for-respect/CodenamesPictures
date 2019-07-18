package com.pressfforrespect.codenamespictures;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pressfforrespect.codenamespictures.game.Team;

public class GameEndFragment extends Fragment {

    private Button exit;
    private TextView winner;
    private Team winnerTeam;
    private GameActivity game;

    public GameEndFragment(GameActivity game){
        this.game = game;
    }


    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_game_end, container, false);

        exit = fragmentView.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.finishGame();
            }
        });

        winner = fragmentView.findViewById(R.id.winner_team);
        if(winnerTeam == Team.BLUE) {
            winner.setText("Blue Team Won!");
            container.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorBlue)));
        }else {
            winner.setText("Red Team Won!");
            container.setBackgroundColor(Color.parseColor(getResources().getString(R.color.colorRed)));
        }

        return fragmentView;
    }

    public void setWinner(Team team){
        winnerTeam = team;
    }
}
